/* Generated By:JavaCC: Do not edit this line. StandardTokenizer.java */
package org.exist.storage.analysis;

import java.io.*;

/** A grammar-based tokenizer constructed with JavaCC.
 *
 * <p> This should be a good tokenizer for most European-language documents.
 *
 * <p>Many applications have specific tokenizer needs.  If this tokenizer does
 * not suit your application, please consider copying this source code
 * directory to your project and maintaining your own grammar-based tokenizer.
 */
public class StandardTokenizer implements Tokenizer, StandardTokenizerConstants {

  private boolean stem = false;

  public StandardTokenizer() {
    this(new StringReader(""));
  }

  /** Constructs a tokenizer for this Reader. */
  public StandardTokenizer(String text) {
    this(new StringReader(text));
  }

  public void setStemming(boolean stem) {
    this.stem = stem;
  }

  public void setText(String text) {
    ReInit(new StringReader(text));
  }

  public TextToken nextToken() {
    return nextToken( false );
  }

  public TextToken nextToken(boolean wildcards) {
    try {
      return internalNextToken();
    } catch( ParseException e ) {
      e.printStackTrace();
    }
    return null;
  }

  public static void main(String args[]) throws Exception {
    StandardTokenizer tokenizer =
      new StandardTokenizer("test da\u00df 2.55 j\u00fcrgen hallo " +
        "www.sozionet.org 1,235.01 meier@ifs.tu-darmstadt.de " +
        "http://www.exist-db.org A3 Z39.50");
    TextToken tok;
    while((tok = tokenizer.nextToken()) != null) {
      System.out.println(tok.getText());
    }
  }

/** Returns the next token in the stream, or null at EOS.
 * <p>The returned token's type is set to an element of {@link
 * StandardTokenizerConstants#tokenImage}.
 */
  final public TextToken internalNextToken() throws ParseException {
  Token token = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ALPHA:
      token = jj_consume_token(ALPHA);
    {if (true) return new TextToken(TextToken.ALPHA, token.image);}
      break;
    case ALPHANUM:
    case SERIAL:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ALPHANUM:
        token = jj_consume_token(ALPHANUM);
        break;
      case SERIAL:
        token = jj_consume_token(SERIAL);
        break;
      default:
        jj_la1[0] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    {if (true) return new TextToken(TextToken.ALPHANUM, token.image);}
      break;
    case NUMERIC:
      token = jj_consume_token(NUMERIC);
    {if (true) return new TextToken(TextToken.NUMBER, token.image);}
      break;
    case EMAIL:
      token = jj_consume_token(EMAIL);
    {if (true) return new TextToken(TextToken.EMAIL, token.image);}
      break;
    case HOST:
      token = jj_consume_token(HOST);
    {if (true) return new TextToken(TextToken.HOST, token.image);}
      break;
    case ACRONYM:
      token = jj_consume_token(ACRONYM);
    {if (true) return new TextToken(TextToken.ACRONYM, token.image);}
      break;
    case 0:
      token = jj_consume_token(0);
    {if (true) return null;}
      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  public StandardTokenizerTokenManager token_source;
  SimpleCharStream jj_input_stream;
  public Token token, jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[2];
  final private int[] jj_la1_0 = {0x82,0xff,};

  public StandardTokenizer(java.io.InputStream stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new StandardTokenizerTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  public void ReInit(java.io.InputStream stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  public StandardTokenizer(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new StandardTokenizerTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  public StandardTokenizer(StandardTokenizerTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  public void ReInit(StandardTokenizerTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  final private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  final private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.Vector jj_expentries = new java.util.Vector();
  private int[] jj_expentry;
  private int jj_kind = -1;

  final public ParseException generateParseException() {
    jj_expentries.removeAllElements();
    boolean[] la1tokens = new boolean[14];
    for (int i = 0; i < 14; i++) {
      la1tokens[i] = false;
    }
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 2; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 14; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.addElement(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = (int[])jj_expentries.elementAt(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  final public void enable_tracing() {
  }

  final public void disable_tracing() {
  }

}
