package org.exist.launcher;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.xml.transform.TransformerException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.exist.storage.BrokerPool;
import org.exist.storage.CollectionCacheManager;
import org.exist.storage.DefaultCacheManager;
import org.exist.util.Configuration;
import org.exist.util.ConfigurationHelper;
import org.exist.util.DatabaseConfigurationException;

/**
 *
 * @author wolf
 */


public class ConfigurationDialog extends JDialog {

    private final Launcher launcher;
    private boolean changed = false;
    private boolean dataDirChanged = false;
    private boolean beforeStart = false;

    /**
     * Creates new form ConfigurationDialog
     */
    public ConfigurationDialog(Launcher launcher) {
        setModal(true);
        setTitle("eXist-db System Configuration");

        initComponents();

        this.launcher = launcher;
        
        final Properties vmProperties = LauncherWrapper.getVMProperties();
        try {
            Configuration existConfig = new Configuration();
            final int cacheSizeProp = existConfig.getInteger(DefaultCacheManager.PROPERTY_CACHE_SIZE);
            cacheSize.setValue(new Integer(cacheSizeProp));
            
            final int collectionCacheProp = existConfig.getInteger(CollectionCacheManager.PROPERTY_CACHE_SIZE);
            collectionCache.setValue(new Integer(collectionCacheProp));

            final String dir = existConfig.getProperty(BrokerPool.PROPERTY_DATA_DIR).toString();
            dataDir.setText(dir);
        } catch (DatabaseConfigurationException ex) {
            Logger.getLogger(ConfigurationDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        final String maxMemProp = vmProperties.getProperty("memory.max", "1024");
        maxMemory.setValue(new Integer(maxMemProp));
        final String minMemProp = vmProperties.getProperty("memory.min", "64");
        minMemory.setValue(new Integer(minMemProp));
        
        checkCacheBoundaries();

        changed = false;

        final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(d.width - this.getWidth() - 40, 60);
        //setLocationRelativeTo(null);

        setAlwaysOnTop(true);
    }

    public void open(boolean firstStart) {
        if (firstStart) {
            beforeStart = true;
            // always check data dir on first start
            dataDirChanged = true;
            btnCancel.setVisible(false);
            lbStartupMsg.setVisible(true);
            separator.setVisible(true);

            if (SystemUtils.IS_OS_MAC_OSX) {
                File dir = new File(System.getProperty("user.home") + "/Library/Application Support/org.exist");
                dataDir.setText(dir.getAbsolutePath());
            }
        } else {
            lbStartupMsg.setVisible(false);
            separator.setVisible(false);
        }
        setVisible(true);
        requestFocus();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lbExistLogo = new javax.swing.JLabel();
        separator = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        minMemory = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        maxMemory = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cacheSize = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        collectionCache = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        lbCurrentUsage = new javax.swing.JLabel();
        lbStartupMsg = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        dataDir = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnPanel = new javax.swing.JPanel();
        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnSelectDir = new javax.swing.JButton();

        setTitle("eXist-db Configuration");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        lbExistLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/exist/client/icons/x.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.insets = new java.awt.Insets(0, 16, 0, 6);
        getContentPane().add(lbExistLogo, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 22);
        getContentPane().add(separator, gridBagConstraints);

        jLabel1.setText("Min Memory");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 22, 0, 0);
        getContentPane().add(jLabel1, gridBagConstraints);

        minMemory.setModel(new javax.swing.SpinnerNumberModel(64, 64, 256, 64));
        minMemory.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                minMemoryStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        getContentPane().add(minMemory, gridBagConstraints);

        jLabel2.setText("Max Memory");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 22, 0, 0);
        getContentPane().add(jLabel2, gridBagConstraints);

        maxMemory.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1024), Integer.valueOf(512), null, Integer.valueOf(64)));
        maxMemory.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                maxMemoryChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        getContentPane().add(maxMemory, gridBagConstraints);

        jLabel3.setFont(jLabel3.getFont().deriveFont(jLabel3.getFont().getStyle() | java.awt.Font.BOLD));
        jLabel3.setText("Java Memory");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(26, 22, 16, 0);
        getContentPane().add(jLabel3, gridBagConstraints);

        jLabel4.setFont(jLabel4.getFont().deriveFont(jLabel4.getFont().getStyle() | java.awt.Font.BOLD));
        jLabel4.setText("Caches");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(26, 22, 16, 0);
        getContentPane().add(jLabel4, gridBagConstraints);

        jLabel5.setText("General Cache");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 22, 0, 0);
        getContentPane().add(jLabel5, gridBagConstraints);

        cacheSize.setModel(new javax.swing.SpinnerNumberModel(128, 48, 256, 16));
        cacheSize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                cacheSizeStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        getContentPane().add(cacheSize, gridBagConstraints);

        jLabel7.setText("Collection Cache");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 22, 0, 0);
        getContentPane().add(jLabel7, gridBagConstraints);

        collectionCache.setModel(new javax.swing.SpinnerNumberModel(48, 48, 256, 16));
        collectionCache.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                collectionCacheStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        getContentPane().add(collectionCache, gridBagConstraints);

        jLabel8.setText("<html><p>Memory settings only become effective after restart and only apply when eXist is started via the system tray launcher.</p></html>");
        jLabel8.setPreferredSize(new java.awt.Dimension(280, 48));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 13, 0, 22);
        getContentPane().add(jLabel8, gridBagConstraints);

        lbCurrentUsage.setText("Memory usage (in MB):");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 22, 12, 0);
        getContentPane().add(lbCurrentUsage, gridBagConstraints);

        lbStartupMsg.setFont(lbStartupMsg.getFont().deriveFont(lbStartupMsg.getFont().getStyle() | java.awt.Font.BOLD));
        lbStartupMsg.setText("<html><p>It seems you are starting eXist for the first time. Please configure your memory settings below.</p></html>");
        lbStartupMsg.setMinimumSize(new java.awt.Dimension(60, 64));
        lbStartupMsg.setPreferredSize(new java.awt.Dimension(300, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(27, 22, 0, 0);
        getContentPane().add(lbStartupMsg, gridBagConstraints);

        jLabel9.setText("<html><p>Changing the data directory will create an empty database in the new location (unless there's already data in it).</p></html>");
        jLabel9.setPreferredSize(new java.awt.Dimension(280, 48));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 13, 0, 22);
        getContentPane().add(jLabel9, gridBagConstraints);

        jLabel10.setFont(jLabel10.getFont().deriveFont(jLabel10.getFont().getStyle() | java.awt.Font.BOLD));
        jLabel10.setText("Data Directory");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(26, 22, 16, 0);
        getContentPane().add(jLabel10, gridBagConstraints);

        dataDir.setMinimumSize(new java.awt.Dimension(180, 28));
        dataDir.setPreferredSize(new java.awt.Dimension(180, 28));
        dataDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataDirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 22, 0, 0);
        getContentPane().add(dataDir, gridBagConstraints);

        jLabel11.setText("<html><p>Total cache size should not exceed 1/3 of max memory unless you have more than 2GB available. These sizes are in megabytes.</p></html>");
        jLabel11.setPreferredSize(new java.awt.Dimension(280, 48));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 13, 0, 22);
        getContentPane().add(jLabel11, gridBagConstraints);

        btnPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        btnPanel.add(btnCancel);

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveConfig(evt);
            }
        });
        btnPanel.add(btnSave);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(36, 13, 8, 0);
        getContentPane().add(btnPanel, gridBagConstraints);

        btnSelectDir.setText("Select");
        btnSelectDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectDirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        getContentPane().add(btnSelectDir, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        setVisible(false);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void maxMemoryChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_maxMemoryChanged
        checkCacheBoundaries();
        changed = true;
    }//GEN-LAST:event_maxMemoryChanged

    private boolean checkDataDir() {
        if (!dataDirChanged)
            return true;
        File dir = new File(dataDir.getText());
        if (dir.exists()) {
            Collection<File> files = FileUtils.listFiles(dir, new String[]{"dbx"}, false);
            if (files.size() > 0) {
                final int r = JOptionPane.showConfirmDialog(this, "The specified data directory already contains data. " +
                        "Do you want to use this? Data will not be removed.", "Confirm Data Directory", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                if (r == JOptionPane.OK_OPTION) {
                    return true;
                }
                return false;
            }
        } else {
            final int r = JOptionPane.showConfirmDialog(this, "The specified data directory does not exist. Do you want to create it?",
                "Create data directory?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (r == JOptionPane.YES_OPTION) {
                try {
                    FileUtils.forceMkdir(dir);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Failed to create data directory: " + dir.getAbsolutePath(),
                            "Failed to create directory", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                return true;
            }
            return false;
        }
        if (!dir.canWrite()) {
            JOptionPane.showMessageDialog(this, "The specified data directory is not writable. " +
                    "Please choose a different one.", "Data Directory Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void saveConfig(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveConfig
        if (!beforeStart && !changed && !dataDirChanged) {
            setVisible(false);
            return;
        }
        if (!checkDataDir())
            return;
        try {
            Properties properties = new Properties();
            properties.setProperty("memory.max", maxMemory.getValue().toString());
            properties.setProperty("memory.min", minMemory.getValue().toString());
            ConfigurationUtility.saveProperties(properties);

            properties.clear();
            properties.setProperty("cacheSize", cacheSize.getValue().toString());
            properties.setProperty("collectionCache", collectionCache.getValue().toString());
            properties.setProperty("dataDir", dataDir.getText());
            ConfigurationUtility.saveConfiguration(properties);
            if (beforeStart) {
                beforeStart = false;
                btnCancel.setVisible(true);
                setVisible(false);
                this.launcher.shutdown(true);
            } else if (changed || dataDirChanged) {
                int r = JOptionPane.showConfirmDialog(this, "Database needs to be restarted to apply the " +
                            "new settings.", "Confirm restart", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (r == JOptionPane.YES_OPTION) {
                    changed = false;
                    dataDirChanged = false;
                    this.launcher.shutdown(true);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save Java settings: " + e.getMessage(),
                    "Save Error", JOptionPane.ERROR_MESSAGE);
        } catch (TransformerException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save configuration: " + e.getMessage() +
                    " at " + e.getLocationAsString(),
                    "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_saveConfig

    private void cacheSizeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_cacheSizeStateChanged
        changed = true;
        checkCacheBoundaries();
    }//GEN-LAST:event_cacheSizeStateChanged

    private void collectionCacheStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_collectionCacheStateChanged
        changed = true;
    }//GEN-LAST:event_collectionCacheStateChanged

    private void minMemoryStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_minMemoryStateChanged
        changed = true;
    }//GEN-LAST:event_minMemoryStateChanged

    private void dataDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataDirActionPerformed
        dataDirChanged = true;
    }//GEN-LAST:event_dataDirActionPerformed

    private void btnSelectDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectDirActionPerformed
        File currentDir = new File(dataDir.getText());
        if (!currentDir.exists()) {
            currentDir = ConfigurationHelper.getExistHome();
        }
        final JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        chooser.setCurrentDirectory(currentDir);

        if(chooser.showDialog(this, "Choose Data Directory") == JFileChooser.APPROVE_OPTION) {
            dataDir.setText(chooser.getSelectedFile().getAbsolutePath());
            dataDirChanged = true;
        }
    }//GEN-LAST:event_btnSelectDirActionPerformed

    private void checkCacheBoundaries() {
        showCurrentMem();
        final int max = (Integer)maxMemory.getValue();
        final SpinnerNumberModel cacheModel = (SpinnerNumberModel) cacheSize.getModel();
        final SpinnerNumberModel collectionCacheModel = (SpinnerNumberModel) collectionCache.getModel();
        int maxCache;
        if (max <= 2048) {
            maxCache = (max / 3);
        } else {
            maxCache = (max / 2);
        }
        cacheModel.setMaximum(maxCache - 48);
        if (cacheModel.getMaximum().compareTo(cacheModel.getValue()) < 0) {
            cacheModel.setValue(cacheModel.getMaximum());
        }
        collectionCacheModel.setMaximum(maxCache - (Integer)cacheModel.getValue());
        if (collectionCacheModel.getMaximum().compareTo(collectionCacheModel.getValue()) < 0) {
            collectionCacheModel.setValue(collectionCacheModel.getMaximum());
        }
    }

    private void showCurrentMem() {
        lbCurrentUsage.setText("Memory usage: " + (Runtime.getRuntime().freeMemory() / 1024 / 1024) +
            " free/" + (Runtime.getRuntime().maxMemory() / 1024 / 1024) + " max mb");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JPanel btnPanel;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSelectDir;
    private javax.swing.JSpinner cacheSize;
    private javax.swing.JSpinner collectionCache;
    private javax.swing.JTextField dataDir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lbCurrentUsage;
    private javax.swing.JLabel lbExistLogo;
    private javax.swing.JLabel lbStartupMsg;
    private javax.swing.JSpinner maxMemory;
    private javax.swing.JSpinner minMemory;
    private javax.swing.JSeparator separator;
    // End of variables declaration//GEN-END:variables

}
