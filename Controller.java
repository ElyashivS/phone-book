import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;

import javax.swing.*;
import java.io.*;

/**
 * This class represents the controller of the frontend.
 */
public class Controller {

    PhoneBook<String, String> pb = new PhoneBook<>();
    int selected = 0;

    @FXML
    private Label label;

    @FXML
    private ListView<String> listView1;

    @FXML
    private ListView<String> listView2;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField phoneNumTxt;

    @FXML
    public void initialize() {

        // Put some keys and values
        pb.getTreeMap().put("A-One", "111");
        pb.getTreeMap().put("B-Two", "222");
        pb.getTreeMap().put("C-Three", "333");
        pb.getTreeMap().put("D-Four", "444");
        pb.getTreeMap().put("E-Five", "555");
        pb.getTreeMap().put("F-Six", "666");
        pb.getTreeMap().put("G-Seven", "777");
        pb.getTreeMap().put("H-Eight", "888");
        pb.getTreeMap().put("I-Nine", "999");
        pb.getTreeMap().put("J-Ten", "100");
        pb.getTreeMap().put("K-Eleven", "110");
        pb.getTreeMap().put("L-Twelve", "120");
        pb.getTreeMap().put("M-Thirteen", "130");
        listView1.getItems().addAll(pb.getTreeMap().keySet());
        listView2.getItems().addAll(pb.getTreeMap().values());

        listener(listView1, listView2);
        listener(listView2, listView1);

        // Make the two lists scroll together.
        listView1.addEventFilter(ScrollEvent.ANY, e -> {
            Node n1 = listView1.lookup(".scroll-bar");
            if (n1 instanceof ScrollBar bar1) {
                Node n2 = listView2.lookup(".scroll-bar");
                if (n2 instanceof ScrollBar bar2) {
                    bar1.valueProperty().bindBidirectional(bar2.valueProperty());
                }
            }
        });
    }

    // Make the 2 lists getting the same selected lines.
    private void listener(ListView<String> listView1, ListView<String> listView2) {
        listView1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                selected = listView1.getSelectionModel().getSelectedIndex();
                listView2.scrollTo(selected);
                listView2.getSelectionModel().select(selected);
            }
        });
    }

    // This function add name and phone number to the phonebook.
    @FXML
    private void addPressed(ActionEvent event) {
        String name = nameTxt.getText();
        String number = phoneNumTxt.getText();
        if (!pb.getTreeMap().containsKey(name)) { // If the name ISN'T contain in the phonebook
            label.setText("");
            addOrUpdate(name, number);
        } else {
            label.setText("This name already contains in the Phonebook.");
        }
    }

    // This function delete name and phone number from the phonebook
    @FXML
    private void deletePressed(ActionEvent event) {
        // The temps is here because without them it'll remove the first item, and won't be synchronized with the second
        String temp1 = listView1.getSelectionModel().getSelectedItem();
        String temp2 = listView2.getSelectionModel().getSelectedItem();
        if (!(temp1 == null || temp2 == null)) {
            listView1.getItems().remove(temp1);
            listView2.getItems().remove(temp2);
            pb.getTreeMap().remove(temp1);
            label.setText("");
        } else {
            label.setText("Please select name or phone number to delete");
        }
    }

    // This function find a name in the phonebook and remark it.
    @FXML
    private void searchPressed(ActionEvent event) {
        int index;
        String searched = nameTxt.getText();
        for (int i = 0; i < listView1.getItems().size(); i++) {
            if (searched.equals(listView1.getItems().get(i))) {
                index = i;
                listView1.scrollTo(index);
                listView1.getSelectionModel().select(index);
                label.setText("");
                break;
            } else {
                label.setText("This name is not contains in the Phonebook.");
            }
        }
    }

    // This function update the number of un exist contact.
    @FXML
    private void updatePressed(ActionEvent event) {
        String name = nameTxt.getText();
        String number = phoneNumTxt.getText();
        if (pb.getTreeMap().containsKey(name)) {
            label.setText("");
            addOrUpdate(name, number);
        } else {
            label.setText("This name is not contains in the Phonebook.");
        }
    }

    // This function save the current phonebook to a text file.
    @FXML
    private void savePressed(ActionEvent event) {
        try {
            FileOutputStream f = new FileOutputStream("MyPhonebook.txt");
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            o.writeObject(pb);

            o.close();
            f.close();

            listView1.getItems().clear();
            listView2.getItems().clear();
            listView1.getItems().addAll(pb.getTreeMap().keySet());
            listView2.getItems().addAll(pb.getTreeMap().values());

            JOptionPane.showMessageDialog(null, "Your Phonebook saved successfully " +
                    "in the folder \"src\". The name of the file is: MyPhonebook.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This function load a saved file of phonebook
    @FXML
    private void loadPressed(ActionEvent event) {
        try {
            FileInputStream fi = new FileInputStream("MyPhonebook.txt");
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
            pb = (PhoneBook<String, String>) oi.readObject();

            oi.close();
            fi.close();

            listView1.getItems().clear();
            listView2.getItems().clear();
            listView1.getItems().addAll(pb.getTreeMap().keySet());
            listView2.getItems().addAll(pb.getTreeMap().values());

            JOptionPane.showMessageDialog(null, "Your Phonebook has been loaded successfully");
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    // This function checks that the name and phone number text field aren't empty and update the phonebook.
    private void addOrUpdate(String name, String number) {
        if (name.equals("") || number.equals("")) {
            label.setText("Please enter name and number");
        } else if (!number.matches("[0-9]*")) {
            label.setText("Please enter only numbers at the Phone Number text field");
        } else {
            pb.getTreeMap().put(name, number);
            listView1.getItems().clear();
            listView2.getItems().clear();
            listView1.getItems().addAll(pb.getTreeMap().keySet());
            listView2.getItems().addAll(pb.getTreeMap().values());
            label.setText("");
        }
    }
}
