define("ace/mode/scala",["require","exports","module","ace/lib/oop","ace/mode/javascript","ace/tokenizer","ace/mode/scala_highlight_rules","ace/mode/matching_brace_outdent","ace/mode/behaviour/cstyle"],function(a,b,c){var d=a("../lib/oop"),e=a("./javascript").Mode,f=a("../tokenizer").Tokenizer,g=a("./scala_highlight_rules").ScalaHighlightRules,h=a("./matching_brace_outdent").MatchingBraceOutdent,i=a("./behaviour/cstyle").CstyleBehaviour,j=function(){this.$tokenizer=new f((new g).getRules()),this.$outdent=new h,this.$behaviour=new i};d.inherits(j,e),function(){this.createWorker=function(a){return null}}.call(j.prototype),b.Mode=j}),define("ace/mode/javascript",["require","exports","module","ace/lib/oop","ace/mode/text","ace/tokenizer","ace/mode/javascript_highlight_rules","ace/mode/matching_brace_outdent","ace/range","ace/worker/worker_client","ace/mode/behaviour/cstyle"],function(a,b,c){var d=a("../lib/oop"),e=a("./text").Mode,f=a("../tokenizer").Tokenizer,g=a("./javascript_highlight_rules").JavaScriptHighlightRules,h=a("./matching_brace_outdent").MatchingBraceOutdent,i=a("../range").Range,j=a("../worker/worker_client").WorkerClient,k=a("./behaviour/cstyle").CstyleBehaviour,l=function(){this.$tokenizer=new f((new g).getRules()),this.$outdent=new h,this.$behaviour=new k};d.inherits(l,e),function(){this.toggleCommentLines=function(a,b,c,d){var e=!0,f=[],g=/^(\s*)\/\//;for(var h=c;h<=d;h++)if(!g.test(b.getLine(h))){e=!1;break}if(e){var j=new i(0,0,0,0);for(var h=c;h<=d;h++){var k=b.getLine(h),l=k.match(g);j.start.row=h,j.end.row=h,j.end.column=l[0].length,b.replace(j,l[1])}}else b.indentRows(c,d,"//")},this.getNextLineIndent=function(a,b,c){var d=this.$getIndent(b),e=this.$tokenizer.getLineTokens(b,a),f=e.tokens,g=e.state;if(f.length&&f[f.length-1].type=="comment")return d;if(a=="start"||a=="regex_allowed"){var h=b.match(/^.*[\{\(\[\:]\s*$/);h&&(d+=c)}else if(a=="doc-start"){if(g=="start"||a=="regex_allowed")return"";var h=b.match(/^\s*(\/?)\*/);h&&(h[1]&&(d+=" "),d+="* ")}return d},this.checkOutdent=function(a,b,c){return this.$outdent.checkOutdent(b,c)},this.autoOutdent=function(a,b,c){this.$outdent.autoOutdent(b,c)},this.createWorker=function(a){var b=new j(["ace"],"worker-javascript.js","ace/mode/javascript_worker","JavaScriptWorker");return b.attachToDocument(a.getDocument()),b.on("jslint",function(b){var c=[];for(var d=0;d<b.data.length;d++){var e=b.data[d];e&&c.push({row:e.line-1,column:e.character-1,text:e.reason,type:"warning",lint:e})}a.setAnnotations(c)}),b.on("narcissus",function(b){a.setAnnotations([b.data])}),b.on("terminate",function(){a.clearAnnotations()}),b}}.call(l.prototype),b.Mode=l}),define("ace/mode/javascript_highlight_rules",["require","exports","module","ace/lib/oop","ace/lib/lang","ace/unicode","ace/mode/doc_comment_highlight_rules","ace/mode/text_highlight_rules"],function(a,b,c){var d=a("../lib/oop"),e=a("../lib/lang"),f=a("../unicode"),g=a("./doc_comment_highlight_rules").DocCommentHighlightRules,h=a("./text_highlight_rules").TextHighlightRules,i=function(){var a=e.arrayToMap("Array|Boolean|Date|Function|Iterator|Number|Object|RegExp|String|Proxy|Namespace|QName|XML|XMLList|ArrayBuffer|Float32Array|Float64Array|Int16Array|Int32Array|Int8Array|Uint16Array|Uint32Array|Uint8Array|Uint8ClampedArray|Error|EvalError|InternalError|RangeError|ReferenceError|StopIteration|SyntaxError|TypeError|URIError|decodeURI|decodeURIComponent|encodeURI|encodeURIComponent|eval|isFinite|isNaN|parseFloat|parseInt|JSON|Math|this|arguments|prototype|window|document".split("|")),b=e.arrayToMap("break|case|catch|continue|default|delete|do|else|finally|for|function|if|in|instanceof|new|return|switch|throw|try|typeof|let|var|while|with|const|yield|import|get|set".split("|")),c="case|do|else|finally|in|instanceof|return|throw|try|typeof|yield",d=e.arrayToMap("__parent__|__count__|escape|unescape|with|__proto__".split("|")),h=e.arrayToMap("const|let|var|function".split("|")),i=e.arrayToMap("null|Infinity|NaN|undefined".split("|")),j=e.arrayToMap("class|enum|extends|super|export|implements|private|public|interface|package|protected|static".split("|")),k="["+f.packages.L+"\\$_]["+f.packages.L+f.packages.Mn+f.packages.Mc+f.packages.Nd+f.packages.Pc+"\\$_]*\\b";this.$rules={start:[{token:"comment",regex:"\\/\\/.*$"},(new g).getStartRule("doc-start"),{token:"comment",merge:!0,regex:"\\/\\*",next:"comment"},{token:"string",regex:'["](?:(?:\\\\.)|(?:[^"\\\\]))*?["]'},{token:"string",merge:!0,regex:'["].*\\\\$',next:"qqstring"},{token:"string",regex:"['](?:(?:\\\\.)|(?:[^'\\\\]))*?[']"},{token:"string",merge:!0,regex:"['].*\\\\$",next:"qstring"},{token:"constant.numeric",regex:"0[xX][0-9a-fA-F]+\\b"},{token:"constant.numeric",regex:"[+-]?\\d+(?:(?:\\.\\d*)?(?:[eE][+-]?\\d+)?)?\\b"},{token:["keyword.definition","text","entity.name.function"],regex:"(function)(\\s+)("+k+")"},{token:"constant.language.boolean",regex:"(?:true|false)\\b"},{token:"keyword",regex:"(?:"+c+")\\b",next:"regex_allowed"},{token:function(c){return a.hasOwnProperty(c)?"variable.language":d.hasOwnProperty(c)?"invalid.deprecated":h.hasOwnProperty(c)?"keyword.definition":b.hasOwnProperty(c)?"keyword":i.hasOwnProperty(c)?"constant.language":j.hasOwnProperty(c)?"invalid.illegal":c=="debugger"?"invalid.deprecated":"identifier"},regex:k},{token:"keyword.operator",regex:"!|\\$|%|&|\\*|\\-\\-|\\-|\\+\\+|\\+|~|===|==|=|!=|!==|<=|>=|<<=|>>=|>>>=|<>|<|>|!|&&|\\|\\||\\?\\:|\\*=|%=|\\+=|\\-=|&=|\\^=|\\b(?:in|instanceof|new|delete|typeof|void)",next:"regex_allowed"},{token:"punctuation.operator",regex:"\\?|\\:|\\,|\\;|\\.",next:"regex_allowed"},{token:"paren.lparen",regex:"[[({]",next:"regex_allowed"},{token:"paren.rparen",regex:"[\\])}]"},{token:"keyword.operator",regex:"\\/=?",next:"regex_allowed"},{token:"comment",regex:"^#!.*$"},{token:"text",regex:"\\s+"}],regex_allowed:[{token:"comment",merge:!0,regex:"\\/\\*",next:"comment_regex_allowed"},{token:"comment",regex:"\\/\\/.*$"},{token:"string.regexp",regex:"\\/(?:(?:\\[(?:\\\\]|[^\\]])+\\])|(?:\\\\/|[^\\]/]))*[/]\\w*",next:"start"},{token:"text",regex:"\\s+"},{token:"empty",regex:"",next:"start"}],comment_regex_allowed:[{token:"comment",regex:".*?\\*\\/",merge:!0,next:"regex_allowed"},{token:"comment",merge:!0,regex:".+"}],comment:[{token:"comment",regex:".*?\\*\\/",merge:!0,next:"start"},{token:"comment",merge:!0,regex:".+"}],qqstring:[{token:"string",regex:'(?:(?:\\\\.)|(?:[^"\\\\]))*?"',next:"start"},{token:"string",merge:!0,regex:".+"}],qstring:[{token:"string",regex:"(?:(?:\\\\.)|(?:[^'\\\\]))*?'",next:"start"},{token:"string",merge:!0,regex:".+"}]},this.embedRules(g,"doc-",[(new g).getEndRule("start")])};d.inherits(i,h),b.JavaScriptHighlightRules=i}),define("ace/mode/doc_comment_highlight_rules",["require","exports","module","ace/lib/oop","ace/mode/text_highlight_rules"],function(a,b,c){var d=a("../lib/oop"),e=a("./text_highlight_rules").TextHighlightRules,f=function(){this.$rules={start:[{token:"comment.doc.tag",regex:"@[\\w\\d_]+"},{token:"comment.doc",merge:!0,regex:"\\s+"},{token:"comment.doc",merge:!0,regex:"TODO"},{token:"comment.doc",merge:!0,regex:"[^@\\*]+"},{token:"comment.doc",merge:!0,regex:"."}]}};d.inherits(f,e),function(){this.getStartRule=function(a){return{token:"comment.doc",merge:!0,regex:"\\/\\*(?=\\*)",next:a}},this.getEndRule=function(a){return{token:"comment.doc",merge:!0,regex:"\\*\\/",next:a}}}.call(f.prototype),b.DocCommentHighlightRules=f}),define("ace/mode/matching_brace_outdent",["require","exports","module","ace/range"],function(a,b,c){var d=a("../range").Range,e=function(){};((function(){this.checkOutdent=function(a,b){return/^\s+$/.test(a)?/^\s*\}/.test(b):!1},this.autoOutdent=function(a,b){var c=a.getLine(b),e=c.match(/^(\s*\})/);if(!e)return 0;var f=e[1].length,g=a.findMatchingBracket({row:b,column:f});if(!g||g.row==b)return 0;var h=this.$getIndent(a.getLine(g.row));a.replace(new d(b,0,b,f-1),h)},this.$getIndent=function(a){var b=a.match(/^(\s+)/);return b?b[1]:""}})).call(e.prototype),b.MatchingBraceOutdent=e}),define("ace/worker/worker_client",["require","exports","module","ace/lib/oop","ace/lib/event_emitter"],function(a,b,c){var d=a("../lib/oop"),e=a("../lib/event_emitter").EventEmitter,f=function(b,c,d,e){this.changeListener=this.changeListener.bind(this);if(window.require.packaged)var f=this.$guessBasePath(),g=this.$worker=new Worker(f+c);else{var h=this.$normalizePath(a.nameToUrl("ace/worker/worker",null,"_")),g=this.$worker=new Worker(h),i={};for(var j=0;j<b.length;j++){var k=b[j],l=this.$normalizePath(a.nameToUrl(k,null,"_").replace(/.js$/,""));i[k]=l}}this.$worker.postMessage({init:!0,tlns:i,module:d,classname:e}),this.callbackId=1,this.callbacks={};var m=this;this.$worker.onerror=function(a){throw window.console&&console.log&&console.log(a),a},this.$worker.onmessage=function(a){var b=a.data;switch(b.type){case"log":window.console&&console.log&&console.log(b.data);break;case"event":m._dispatchEvent(b.name,{data:b.data});break;case"call":var c=m.callbacks[b.id];c&&(c(b.data),delete m.callbacks[b.id])}}};((function(){d.implement(this,e),this.$normalizePath=function(a){return a.match(/^\w+:/)||(a=location.protocol+"//"+location.host+(a.charAt(0)=="/"?"":location.pathname.replace(/\/[^\/]*$/,""))+"/"+a.replace(/^[\/]+/,"")),a},this.$guessBasePath=function(){if(a.aceBaseUrl)return a.aceBaseUrl;var b=document.getElementsByTagName("script");for(var c=0;c<b.length;c++){var d=b[c],e=d.getAttribute("data-ace-base");if(e)return e.replace(/\/*$/,"/");var f=d.src||d.getAttribute("src");if(!f)continue;var g=f.match(/^(?:(.*\/)ace\.js|(.*\/)ace-uncompressed\.js)(?:\?|$)/);if(g)return g[1]||g[2]}return""},this.terminate=function(){this._dispatchEvent("terminate",{}),this.$worker.terminate(),this.$worker=null,this.$doc.removeEventListener("change",this.changeListener),this.$doc=null},this.send=function(a,b){this.$worker.postMessage({command:a,args:b})},this.call=function(a,b,c){if(c){var d=this.callbackId++;this.callbacks[d]=c,b.push(d)}this.send(a,b)},this.emit=function(a,b){try{this.$worker.postMessage({event:a,data:{data:b.data}})}catch(c){}},this.attachToDocument=function(a){this.$doc&&this.terminate(),this.$doc=a,this.call("setValue",[a.getValue()]),a.on("change",this.changeListener)},this.changeListener=function(a){a.range={start:a.data.range.start,end:a.data.range.end},this.emit("change",a)}})).call(f.prototype),b.WorkerClient=f}),define("ace/mode/behaviour/cstyle",["require","exports","module","ace/lib/oop","ace/mode/behaviour"],function(a,b,c){var d=a("../../lib/oop"),e=a("../behaviour").Behaviour,f=function(){this.add("braces","insertion",function(a,b,c,d,e){if(e=="{"){var f=c.getSelectionRange(),g=d.doc.getTextRange(f);return g!==""?{text:"{"+g+"}",selection:!1}:{text:"{}",selection:[1,1]}}if(e=="}"){var h=c.getCursorPosition(),i=d.doc.getLine(h.row),j=i.substring(h.column,h.column+1);if(j=="}"){var k=d.$findOpeningBracket("}",{column:h.column+1,row:h.row});if(k!==null)return{text:"",selection:[1,1]}}}else if(e=="\n"){var h=c.getCursorPosition(),i=d.doc.getLine(h.row),j=i.substring(h.column,h.column+1);if(j=="}"){var l=d.findMatchingBracket({row:h.row,column:h.column+1});if(!l)return null;var m=this.getNextLineIndent(a,i.substring(0,i.length-1),d.getTabString()),n=this.$getIndent(d.doc.getLine(l.row));return{text:"\n"+m+"\n"+n,selection:[1,m.length,1,m.length]}}}}),this.add("braces","deletion",function(a,b,c,d,e){var f=d.doc.getTextRange(e);if(!e.isMultiLine()&&f=="{"){var g=d.doc.getLine(e.start.row),h=g.substring(e.end.column,e.end.column+1);if(h=="}")return e.end.column++,e}}),this.add("parens","insertion",function(a,b,c,d,e){if(e=="("){var f=c.getSelectionRange(),g=d.doc.getTextRange(f);return g!==""?{text:"("+g+")",selection:!1}:{text:"()",selection:[1,1]}}if(e==")"){var h=c.getCursorPosition(),i=d.doc.getLine(h.row),j=i.substring(h.column,h.column+1);if(j==")"){var k=d.$findOpeningBracket(")",{column:h.column+1,row:h.row});if(k!==null)return{text:"",selection:[1,1]}}}}),this.add("parens","deletion",function(a,b,c,d,e){var f=d.doc.getTextRange(e);if(!e.isMultiLine()&&f=="("){var g=d.doc.getLine(e.start.row),h=g.substring(e.start.column+1,e.start.column+2);if(h==")")return e.end.column++,e}}),this.add("string_dquotes","insertion",function(a,b,c,d,e){if(e=='"'){var f=c.getSelectionRange(),g=d.doc.getTextRange(f);if(g!=="")return{text:'"'+g+'"',selection:!1};var h=c.getCursorPosition(),i=d.doc.getLine(h.row),j=i.substring(h.column-1,h.column);if(j=="\\")return null;var k=d.getTokens(f.start.row,f.start.row)[0].tokens,l=0,m,n=-1;for(var o=0;o<k.length;o++){m=k[o],m.type=="string"?n=-1:n<0&&(n=m.value.indexOf('"'));if(m.value.length+l>f.start.column)break;l+=k[o].value.length}if(!m||n<0&&m.type!=="comment"&&(m.type!=="string"||f.start.column!==m.value.length+l-1&&m.value.lastIndexOf('"')===m.value.length-1))return{text:'""',selection:[1,1]};if(m&&m.type==="string"){var p=i.substring(h.column,h.column+1);if(p=='"')return{text:"",selection:[1,1]}}}}),this.add("string_dquotes","deletion",function(a,b,c,d,e){var f=d.doc.getTextRange(e);if(!e.isMultiLine()&&f=='"'){var g=d.doc.getLine(e.start.row),h=g.substring(e.start.column+1,e.start.column+2);if(h=='"')return e.end.column++,e}})};d.inherits(f,e),b.CstyleBehaviour=f}),define("ace/mode/scala_highlight_rules",["require","exports","module","ace/lib/oop","ace/lib/lang","ace/mode/doc_comment_highlight_rules","ace/mode/text_highlight_rules"],function(a,b,c){var d=a("../lib/oop"),e=a("../lib/lang"),f=a("./doc_comment_highlight_rules").DocCommentHighlightRules,g=a("./text_highlight_rules").TextHighlightRules,h=function(){var a=e.arrayToMap("case|default|do|else|for|if|match|while|throw|return|try|catch|finally|yield|abstract|class|def|extends|final|forSome|implicit|implicits|import|lazy|new|object|override|package|private|protected|sealed|super|this|trait|type|val|var|with".split("|")),b=e.arrayToMap("true|false".split("|")),c=e.arrayToMap("AbstractMethodError|AssertionError|ClassCircularityError|ClassFormatError|Deprecated|EnumConstantNotPresentException|ExceptionInInitializerError|IllegalAccessError|IllegalThreadStateException|InstantiationError|InternalError|NegativeArraySizeException|NoSuchFieldError|Override|Process|ProcessBuilder|SecurityManager|StringIndexOutOfBoundsException|SuppressWarnings|TypeNotPresentException|UnknownError|UnsatisfiedLinkError|UnsupportedClassVersionError|VerifyError|InstantiationException|IndexOutOfBoundsException|ArrayIndexOutOfBoundsException|CloneNotSupportedException|NoSuchFieldException|IllegalArgumentException|NumberFormatException|SecurityException|Void|InheritableThreadLocal|IllegalStateException|InterruptedException|NoSuchMethodException|IllegalAccessException|UnsupportedOperationException|Enum|StrictMath|Package|Compiler|Readable|Runtime|StringBuilder|Math|IncompatibleClassChangeError|NoSuchMethodError|ThreadLocal|RuntimePermission|ArithmeticException|NullPointerException|Long|Integer|Short|Byte|Double|Number|Float|Character|Boolean|StackTraceElement|Appendable|StringBuffer|Iterable|ThreadGroup|Runnable|Thread|IllegalMonitorStateException|StackOverflowError|OutOfMemoryError|VirtualMachineError|ArrayStoreException|ClassCastException|LinkageError|NoClassDefFoundError|ClassNotFoundException|RuntimeException|Exception|ThreadDeath|Error|Throwable|System|ClassLoader|Cloneable|Class|CharSequence|Comparable|String|Object|Unit|Any|AnyVal|AnyRef|Null|ScalaObject|Singleton|Seq|Iterable|List|Option|Array|Char|Byte|Short|Int|Long|Nothing".split("|")),d=e.arrayToMap("".split("|"));this.$rules={start:[{token:"comment",regex:"\\/\\/.*$"},(new f).getStartRule("doc-start"),{token:"comment",merge:!0,regex:"\\/\\*",next:"comment"},{token:"string.regexp",regex:"[/](?:(?:\\[(?:\\\\]|[^\\]])+\\])|(?:\\\\/|[^\\]/]))*[/]\\w*\\s*(?=[).,;]|$)"},{token:"string",regex:'["](?:(?:\\\\.)|(?:[^"\\\\]))*?["]'},{token:"string",regex:"['](?:(?:\\\\.)|(?:[^'\\\\]))*?[']"},{token:"constant.numeric",regex:"0[xX][0-9a-fA-F]+\\b"},{token:"constant.numeric",regex:"[+-]?\\d+(?:(?:\\.\\d*)?(?:[eE][+-]?\\d+)?)?\\b"},{token:"constant.language.boolean",regex:"(?:true|false)\\b"},{token:function(e){return e=="this"?"variable.language":a.hasOwnProperty(e)?"keyword":c.hasOwnProperty(e)?"support.function":d.hasOwnProperty(e)?"support.function":b.hasOwnProperty(e)?"constant.language":"identifier"},regex:"[a-zA-Z_$][a-zA-Z0-9_$]*\\b"},{token:"keyword.operator",regex:"!|\\$|%|&|\\*|\\-\\-|\\-|\\+\\+|\\+|~|===|==|=|!=|!==|<=|>=|<<=|>>=|>>>=|<>|<|>|!|&&|\\|\\||\\?\\:|\\*=|%=|\\+=|\\-=|&=|\\^=|\\b(?:in|instanceof|new|delete|typeof|void)"},{token:"paren.lparen",regex:"[[({]"},{token:"paren.rparen",regex:"[\\])}]"},{token:"text",regex:"\\s+"}],comment:[{token:"comment",regex:".*?\\*\\/",next:"start"},{token:"comment",merge:!0,regex:".+"}]},this.embedRules(f,"doc-",[(new f).getEndRule("start")])};d.inherits(h,g),b.ScalaHighlightRules=h})