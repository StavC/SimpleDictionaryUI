package Dictionary;

import javax.swing.*;
import java.io.*;
import java.util.Map;
import java.util.TreeMap;

class DictionaryTreeMap {

    final TreeMap<String, String> myDictionary;

    public DictionaryTreeMap() { // constructor for the DictionaryTreeMap

        myDictionary = new TreeMap<>();


    }

    public void readFile() { // reading from a file

        //read from file
        try {

            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fc.showOpenDialog(null);
            if (result == JFileChooser.CANCEL_OPTION) { // if you click cancel ull get an error msg and will have to re run the program
                JOptionPane.showMessageDialog(null, "You Must Choose A File to LOAD otherwise we open blank Dictionary ");


            }
            else {
                File file = fc.getSelectedFile();


                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);

                TreeMap<String, String> mapInFile = (TreeMap<String, String>) ois.readObject();

                ois.close();
                fis.close();



                for (Map.Entry<String, String> entry : mapInFile.entrySet()) { //  adding all the terminologys to the map
                    myDictionary.put(entry.getKey(), entry.getValue());
                }
            } }catch(Exception e){

            }


    }


    public void writeFile(TreeMap<String, String> map) { // writing to which ever file u want

        try {
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fc.showOpenDialog(null);
            if (result == JFileChooser.CANCEL_OPTION) { // if you click cancel ull get an error msg and will have to re run the program
                JOptionPane.showMessageDialog(null, "You Must Choose A File to SAVE");


            }else{
            File file = fc.getSelectedFile();
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(map);
            oos.flush();
            oos.close();
            fos.close();
        }} catch (Exception e) {
        }
    }


}
