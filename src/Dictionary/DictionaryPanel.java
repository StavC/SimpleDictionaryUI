package Dictionary;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class DictionaryPanel extends JFrame implements ActionListener, ListSelectionListener {


    private final JButton loadJButton;
    private final JButton saveJButton;
    private final JButton searchJButton;
    private final JList<String> termJList;
    private final JTextArea explanationJTextArea;
    private final JTextArea searchJTextArea;
    private final JButton addJButton;
    private final JButton deleteJButton;
    private final JButton updateJbutton;
    private final DictionaryTreeMap myDictionary;
    private static final String[] myString = {};
    private final JTextField addTermJtext;
    private final JTextField addExplantionJtext;
    private final JTextField updateExplantionJtext;


    public DictionaryPanel() { // sorting all the GUI to look as i want and adding listeners

        super("Dictionary");
        Box box = Box.createHorizontalBox();
        termJList = new JList<>(myString);
        termJList.addListSelectionListener(this);
        loadJButton = new JButton("Load");
        loadJButton.addActionListener(this);
        saveJButton = new JButton("Save");
        saveJButton.addActionListener(this);
        searchJButton = new JButton("Search");
        searchJButton.addActionListener(this);
        JPanel topJpanel = new JPanel(new GridLayout(1, 2));
        topJpanel.add(saveJButton, BorderLayout.EAST);
        topJpanel.add(loadJButton, BorderLayout.WEST);
        searchJTextArea = new JTextArea("Search here", 1, 1);
        add(topJpanel, BorderLayout.NORTH);
        box.add((new JScrollPane(termJList)));
        addJButton = new JButton("Add");
        addJButton.addActionListener(this);
        deleteJButton = new JButton("Delete");
        deleteJButton.addActionListener(this);
        updateJbutton = new JButton("Update");
        updateJbutton.addActionListener(this);
        box.add(addJButton);
        box.add(deleteJButton);
        box.add(updateJbutton);
        explanationJTextArea = new JTextArea("", 10, 15);
        explanationJTextArea.setEditable(false);
        box.add((new JScrollPane(explanationJTextArea)));
        add(box);
        JPanel bottomJpanel = new JPanel(new GridLayout(1, 2));
        bottomJpanel.add(searchJTextArea);
        bottomJpanel.add(searchJButton);
        add(bottomJpanel, BorderLayout.SOUTH);
        addTermJtext = new JTextField();
        addExplantionJtext = new JTextField();
        updateExplantionJtext =new JTextField();
        myDictionary = new DictionaryTreeMap();


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == loadJButton) {// load file

            myDictionary.myDictionary.clear();
            myDictionary.readFile();
            termJList.setListData(myDictionary.myDictionary.keySet().toArray(new String[]{}));
            explanationJTextArea.setText("Explanation");
            searchJTextArea.setText("Search here");

        }
        if (e.getSource() == deleteJButton) {// delete term

            if(termJList.getSelectedValue()!=null) {
                myDictionary.myDictionary.remove(termJList.getSelectedValue());
                termJList.setListData(myDictionary.myDictionary.keySet().toArray(new String[]{}));
                explanationJTextArea.setText("Explanation");
            }else{

                JOptionPane.showMessageDialog(this,"You cant delete a term if you dont have any terms....");
            }


        }

        if (e.getSource() == updateJbutton) { //update the explantion of the term that selected

           String currentSelected =termJList.getSelectedValue();
           if(termJList.getSelectedValue()!=null) {

               Object[] editMsg = {"Explanation", updateExplantionJtext};

               int option = JOptionPane.showConfirmDialog(this, editMsg, "Adding new Terminology", JOptionPane.OK_CANCEL_OPTION);
               if (option == JOptionPane.OK_OPTION) {
                   myDictionary.myDictionary.replace(termJList.getSelectedValue(), updateExplantionJtext.getText());
                   termJList.setListData(myDictionary.myDictionary.keySet().toArray(new String[]{}));

               } else {
                   System.out.println("Adding Canceled");
               }
               updateExplantionJtext.setText("");
               termJList.setSelectedValue(currentSelected, true);
           }else{
               JOptionPane.showMessageDialog(this,"Select which term you want to update please and try again" );
           }

        }

        if (e.getSource() == saveJButton) {// saving the dictionary to a file
            myDictionary.writeFile(myDictionary.myDictionary);


        }

        if (e.getSource() == searchJButton) {// searching the key

            if (myDictionary.myDictionary.containsKey(searchJTextArea.getText().toLowerCase())) {
                JOptionPane.showMessageDialog(this, "Found it!");


                termJList.setSelectedValue(searchJTextArea.getText().toLowerCase(), true);

            } else {
                JOptionPane.showMessageDialog(this, "Couldnt find this Term");
            }

        }

        if (e.getSource() == addJButton) { //adding new term and explanation
            Object[] message = {"Term", addTermJtext, "Explanation", addExplantionJtext};

            int option = JOptionPane.showConfirmDialog(null, message, "Adding new Terminology", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                myDictionary.myDictionary.put(addTermJtext.getText().toLowerCase(), addExplantionJtext.getText());
                termJList.setListData(myDictionary.myDictionary.keySet().toArray(new String[]{}));

            } else {
                System.out.println("Adding Canceled");
            }
            addTermJtext.setText("");
            addExplantionJtext.setText("");
        }

    }

    @Override
    public void valueChanged(ListSelectionEvent e) { // showing the right explanation for the selected term
        if (termJList.getSelectedValue() != null)
            explanationJTextArea.setText(myDictionary.myDictionary.get(termJList.getSelectedValue()));

    }
}
