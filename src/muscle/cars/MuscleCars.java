/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package muscle.cars;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Adnan Alam
 */
public class MuscleCars extends javax.swing.JFrame{

    Connection conn;
    ResultSet rs;
    PreparedStatement pst;
    
    int car_id = 0;
    
    private DefaultListModel mod;
    
    /**
     * Creates new form LoadingScreen
     */
    public MuscleCars() {
        super("Muscle Cars");
        initComponents();
        setLocationRelativeTo(null);
        conn = JavaConnect.connectDb();
        menu.add(panel);
        mod = new DefaultListModel();
        list.setModel(mod);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent event){
                list = (JList)event.getSource();
                
                if(event.getClickCount() == 2)
                {
                    
                    searchTextField.setText(list.getSelectedValue());
                    String carName = list.getSelectedValue();
                    
                    try
                    {
                        
                        String sql = "SELECT * FROM cars";
                        pst = conn.prepareStatement(sql);
                        rs = pst.executeQuery();
                        
                        while(rs.next())
                        {
                            
                            String stringToCompare = rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4);
                            if(carName.trim().matches(stringToCompare.trim()))
                            {
                                
                                BufferedImage newImage = null;
                                newImage = ImageIO.read(new File("C:\\Users\\Adnan Alam\\Documents\\NetBeansProjects\\Muscle Cars\\pic\\" + rs.getString(6)));
                                Image scaledImage = newImage.getScaledInstance(picLabel.getWidth(), picLabel.getHeight(), Image.SCALE_SMOOTH); 
                                ImageIcon imageIcon = new ImageIcon(scaledImage);
                                picLabel.setIcon(imageIcon);
                                descriptionTextArea.setText(rs.getString(5));
                                descriptionTextArea.setCaretPosition(0);
                                titleLabel.setText(rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
                                car_id = Integer.parseInt(rs.getString(1));
                                
                            } // end if
                            
                        } // end while
                        
                    } // end try
                    catch(Exception exception)
                    {
                        
                        JOptionPane.showMessageDialog(null, exception);
                        
                    } // end catch Exception
                    
                } // end if
                
            } // end method mouseClicked
            
        });
        
        showFirstCar();
        
    } // MuscleCars constructor
    
    public void showFirstCar()
    {
        
        String sql = "SELECT * FROM cars LIMIT 1";
        
        try
        {
            
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            
            if(rs.next())
            {
                
                //pic_file_name = rs.getString(6);
                BufferedImage newImage = null;
                newImage = ImageIO.read(new File("C:\\Users\\Adnan Alam\\Documents\\NetBeansProjects\\Muscle Cars\\pic\\" + rs.getString(6)));
                Image scaledImage = newImage.getScaledInstance(picLabel.getWidth(), picLabel.getHeight(), Image.SCALE_SMOOTH); 
                ImageIcon imageIcon = new ImageIcon(scaledImage);
                picLabel.setIcon(imageIcon);
                descriptionTextArea.setText(rs.getString(5));
                descriptionTextArea.setCaretPosition(0);
                titleLabel.setText(rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
                car_id = Integer.parseInt(rs.getString(1));
                
            } // end if
            
        } // end try
        catch(Exception exception)
        {
            
            JOptionPane.showMessageDialog(null, exception);
            
        } // end catch Exception
        
    } // end method showFirstCar
    
    public int checkNextRecordExists()
    {
        
        String sql = "SELECT * FROM cars WHERE car_id > '"+ car_id +"' ORDER BY car_id LIMIT 1";
        
        int nextRecordExists = 0;
        
        try
        {
            
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            
            if(rs.next())
            {
                
                nextRecordExists = 1;
                
            } // end if
            
        } // end try
        catch(Exception exception)
        {
            
            JOptionPane.showMessageDialog(null, exception);
            
        } // end catch Exception
        
        return nextRecordExists;
        
    } // end method checkNextRecordExists
    
    public int checkPreviousRecordExists()
    {
        
        String sql = "SELECT * FROM cars WHERE car_id < '"+ car_id +"' ORDER BY car_id DESC LIMIT 1";
        
        int previousRecordExists = 0;
        
        try
        {
            
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            
            if(rs.next())
            {
                
                previousRecordExists = 1;
                
            } // end if
            
        } // end try
        catch(Exception exception)
        {
            
            JOptionPane.showMessageDialog(null, exception);
            
        } // end catch Exception
        
        return previousRecordExists;
        
    } // end method previousRecordExists
    
    public void showNextCar()
    {
        
        String sql = "SELECT * FROM cars WHERE car_id > '"+ car_id +"' ORDER BY car_id LIMIT 1";
        
        try
        {
            
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            
            if(rs.next())
            {
                
                BufferedImage newImage = null;
                newImage = ImageIO.read(new File("C:\\Users\\Adnan Alam\\Documents\\NetBeansProjects\\Muscle Cars\\pic\\" + rs.getString(6)));
                Image scaledImage = newImage.getScaledInstance(picLabel.getWidth(), picLabel.getHeight(), Image.SCALE_SMOOTH); 
                ImageIcon imageIcon = new ImageIcon(scaledImage);
                picLabel.setIcon(imageIcon);
                descriptionTextArea.setText(rs.getString(5));
                descriptionTextArea.setCaretPosition(0);
                titleLabel.setText(rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
                car_id = Integer.parseInt(rs.getString(1));

            } // end if
            
        } // end try
        catch(Exception exception)
        {
            
            JOptionPane.showMessageDialog(null, exception);
            
        } // end catch Exception
        
    } // end method showNextCar
    
    public void showPreviousCar()
    {
        
        String sql = "SELECT * FROM cars WHERE car_id < '"+ car_id +"' ORDER BY car_id DESC LIMIT 1";
        
        try
        {
            
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            
            if(rs.next())
            {
                
                BufferedImage newImage = null;
                newImage = ImageIO.read(new File("C:\\Users\\Adnan Alam\\Documents\\NetBeansProjects\\Muscle Cars\\pic\\" + rs.getString(6)));
                Image scaledImage = newImage.getScaledInstance(picLabel.getWidth(), picLabel.getHeight(), Image.SCALE_SMOOTH); 
                ImageIcon imageIcon = new ImageIcon(scaledImage);
                picLabel.setIcon(imageIcon);
                descriptionTextArea.setText(rs.getString(5));
                descriptionTextArea.setCaretPosition(0);
                titleLabel.setText(rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
                car_id = Integer.parseInt(rs.getString(1));

            } // end if
            
        } // end try
        catch(Exception exception)
        {
            
            JOptionPane.showMessageDialog(null, exception);
            
        } // end catch Exception
        
    } // end method showPreviousCar
    
    public void searchString(String keyword)
    {
        
            String sql = "SELECT * FROM cars";
            
            try
            {
                
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                
                while(rs.next())
                {
                    
                    String stringToCompare = rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4);
                    
                    if(stringToCompare.toLowerCase().contains(keyword) || stringToCompare.contains(keyword))
                    {

                        mod.addElement(rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));

                    } // end if
                    //mod.addElement(rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
                    
                } // end while
                menu.show(searchTextField, 0, searchTextField.getHeight());
                
            } // end try
            catch(Exception exception)
            {
                
                JOptionPane.showMessageDialog(null, exception);
                
            } // end catch Exception
            
            
    } // end method searchString
    
    public void searchColumns(String keyword)
    {
        
            String sql = "SELECT * FROM cars WHERE year LIKE '%"+ keyword +"%' OR name LIKE '%"+ keyword +"%' OR model LIKE '%"+ keyword +"%'";

            try
            {

                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();

                while(rs.next())
                {

                    mod.addElement(rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));

                } // end while
                menu.show(searchTextField, 0, searchTextField.getHeight());

            } // end try
            catch(Exception exception)
            {

                JOptionPane.showMessageDialog(null, exception);

            } // end catch Exception
        
    } // end method searchColumns
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        list = new javax.swing.JList<>();
        menu = new javax.swing.JPopupMenu();
        picLabel = new javax.swing.JLabel();
        nextButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionTextArea = new javax.swing.JTextArea();
        titleLabel = new javax.swing.JLabel();
        previousButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        editButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        searchTextField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        jScrollPane2.setViewportView(list);

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
        );

        menu.setFocusable(false);
        menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuMouseClicked(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        picLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        picLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        picLabel.setMaximumSize(new java.awt.Dimension(740, 490));
        picLabel.setMinimumSize(new java.awt.Dimension(740, 490));
        picLabel.setPreferredSize(new java.awt.Dimension(740, 490));

        nextButton.setText("Next");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        descriptionTextArea.setEditable(false);
        descriptionTextArea.setColumns(20);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setRows(5);
        descriptionTextArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(descriptionTextArea);

        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        titleLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        previousButton.setText("Previous");
        previousButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousButtonActionPerformed(evt);
            }
        });

        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\Adnan Alam\\Documents\\NetBeansProjects\\Muscle Cars\\pic\\logo.png")); // NOI18N
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        editButton.setText("Edit");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        searchTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTextFieldKeyReleased(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon("C:\\Users\\Adnan Alam\\Documents\\NetBeansProjects\\Muscle Cars\\pic\\iconfinder_search_172546.png")); // NOI18N

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(785, Short.MAX_VALUE)
                        .addComponent(previousButton, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(nextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(picLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 737, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchTextField))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(picLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(previousButton)
                    .addComponent(nextButton)
                    .addComponent(addButton)
                    .addComponent(editButton)
                    .addComponent(deleteButton))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        showNextCar();
    }//GEN-LAST:event_nextButtonActionPerformed

    private void previousButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousButtonActionPerformed
        showPreviousCar();
    }//GEN-LAST:event_previousButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        NewCarForm newCarForm = new NewCarForm();
        newCarForm.setVisible(true);
        newCarForm.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_addButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        EditForm editForm = new EditForm(car_id);
        editForm.carIdField.setText("" + car_id);
        editForm.setVisible(true);
        editForm.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_editButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        String sql = "DELETE FROM cars WHERE car_id = '"+ car_id +"'";
        
        try
        {
            
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            int nextRecord = checkNextRecordExists();
            int prevRecord = checkPreviousRecordExists();

            if(nextRecord == 1)
            {
                
                showNextCar();
                
            } // end if
            else if(prevRecord == 1)
            {
                
                showPreviousCar();
                
            } // end else if
            else
            {
                
                JOptionPane.showMessageDialog(null, "No Records Available");
                
            } // end else
            
        } // end try
        catch(Exception exception)
        {
            
            JOptionPane.showMessageDialog(null, exception);
            
        } // end catch Exception
        
    }//GEN-LAST:event_deleteButtonActionPerformed
    
    private void searchTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTextFieldKeyReleased
        
        mod.removeAllElements();
        
        String keyword = searchTextField.getText();
        
        if(keyword.trim().isEmpty())
        {
            
            menu.setVisible(false);
            
        } // end if
        else
        {
         
            searchString(keyword);
            //searchColumns(keyword);
            /*
            String sql = "SELECT * FROM cars";
            
            try
            {
                
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                
                while(rs.next())
                {
                    
                    String stringToCompare = rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4);
                    
                    if(stringToCompare.contains(keyword))
                    {

                        mod.addElement(rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));

                    } // end if
                    //mod.addElement(rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
                    
                } // end while
                menu.show(searchTextField, 0, searchTextField.getHeight());
                
            } // end try
            catch(Exception exception)
            {
                
                JOptionPane.showMessageDialog(null, exception);
                
            } // end catch Exception
            */
            
            /*
            String sql = "SELECT * FROM cars WHERE year LIKE '%"+ keyword +"%' OR name LIKE '%"+ keyword +"%' OR model LIKE '%"+ keyword +"%'";

            try
            {

                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();

                while(rs.next())
                {

                    mod.addElement(rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));

                } // end while
                menu.show(searchTextField, 0, searchTextField.getHeight());

            } // end try
            catch(Exception exception)
            {

                JOptionPane.showMessageDialog(null, exception);

            } // end catch Exception
            */
        } // end else
        
    }//GEN-LAST:event_searchTextFieldKeyReleased
   
    private void menuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuMouseClicked
    }//GEN-LAST:event_menuMouseClicked

    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MuscleCars.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MuscleCars.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MuscleCars.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MuscleCars.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MuscleCars().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextArea descriptionTextArea;
    private javax.swing.JButton editButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> list;
    private javax.swing.JPopupMenu menu;
    private javax.swing.JButton nextButton;
    private javax.swing.JPanel panel;
    private javax.swing.JLabel picLabel;
    private javax.swing.JButton previousButton;
    private javax.swing.JTextField searchTextField;
    public javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
