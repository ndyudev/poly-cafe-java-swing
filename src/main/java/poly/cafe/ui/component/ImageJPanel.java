package poly.cafe.ui.component;

import java.io.File;
import java.util.function.Consumer;
import javax.swing.JFileChooser;
import lombok.Getter;
import lombok.Setter;
import poly.cafe.util.XIcon;


public class ImageJPanel extends javax.swing.JPanel {

    /**
     * Creates new form NewJPanel
     */
    public ImageJPanel() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        lblPicture = new javax.swing.JLabel();

        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 102, 102), 1, true));
        setLayout(new java.awt.BorderLayout());

        lblPicture.setFont(new java.awt.Font("Impact", 1, 18)); // NOI18N
        lblPicture.setForeground(new java.awt.Color(255, 102, 102));
        lblPicture.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPicture.setText("Image");
        lblPicture.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblPicture.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPictureMouseClicked(evt);
            }
        });
        add(lblPicture, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void lblPictureMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPictureMouseClicked
        // TODO add your handling code here:
        if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            File file = XIcon.copyTo(fileChooser.getSelectedFile(), this.folder);
            this.setIcon(file.getName());
            if(this.fileChanged != null){
                this.fileChanged.accept(file);
            }
        }
    }//GEN-LAST:event_lblPictureMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JLabel lblPicture;
    // End of variables declaration//GEN-END:variables
    
    @Getter
    @Setter
    String folder = "images";
    Consumer<File> fileChanged;

    public String getIcon() {
        return lblPicture.getToolTipText();
    }

    public void setIcon(String icon) {
        lblPicture.setText("");
        lblPicture.setToolTipText(icon);
        XIcon.setIcon(lblPicture, new File(this.folder, icon));
    }
}
