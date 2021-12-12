import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;

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
        pb.getTreeMap().put("AAA", "aaa");
        pb.getTreeMap().put("BBB", "bbb");
        pb.getTreeMap().put("CCC", "ccc");
        pb.getTreeMap().put("DDD", "ddd");
        pb.getTreeMap().put("EEE", "eee");
        pb.getTreeMap().put("FFF", "fff");
        pb.getTreeMap().put("GGG", "ggg");
        pb.getTreeMap().put("HHH", "hhh");
        pb.getTreeMap().put("III", "iii");
        pb.getTreeMap().put("JJJ", "jjj");
        pb.getTreeMap().put("KKK", "kkk");
        listView1.getItems().addAll(pb.getTreeMap().keySet());
        listView2.getItems().addAll(pb.getTreeMap().values());

        // Make the 2 lists getting the same selected lines.
        listener(listView1, listView2);
        listener(listView2, listView1);

        listView1.addEventFilter(javafx.scene.input.ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                System.out.println("Scroll event!");
//                listView2.scrollTo(listView1.getOnScrollTo());
            }
        });
//        listView1.addEventFilter(javafx.scene.control.ScrollToEvent.ANY, new EventHandler<ScrollToEvent>() {
//            @Override
//            public void handle(ScrollToEvent event) {
//                System.out.println("ScrollToEvent!");
//            }
//        });

    }

    private void listener(ListView<String> listView1, ListView<String> listView2) {
        listView1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                selected = listView1.getSelectionModel().getSelectedIndex();
                listView2.scrollTo(selected);
                listView2.getSelectionModel().select(selected);
            }
        });
    }

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

    private void addOrUpdate(String name, String number) {
        if (!name.equals("") && !number.equals("")) {
            pb.getTreeMap().put(name, number);
            listView1.getItems().clear();
            listView2.getItems().clear();
            listView1.getItems().addAll(pb.getTreeMap().keySet());
            listView2.getItems().addAll(pb.getTreeMap().values());
            label.setText("");
        } else {
            label.setText("Please enter name and number");
        }
    }
}
