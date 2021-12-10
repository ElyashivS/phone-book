import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class Controller {

    PhoneBook pb = new PhoneBook();
    int selected = 0;
    @FXML
    private Button addBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private ListView<String> listView1;

    @FXML
    private ListView<String> listView2;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField phoneNumTxt;

    @FXML
    private Button searchBtn;

    @FXML
    private Button updateBtn;

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
    void addPressed(ActionEvent event) {
        String name = nameTxt.getText();
        String number = phoneNumTxt.getText();
        pb.getTreeMap().put(name, number);

        listView1.getItems().clear();
        listView2.getItems().clear();
        listView1.getItems().addAll(pb.getTreeMap().keySet());
        listView2.getItems().addAll(pb.getTreeMap().values());
    }

    @FXML
    void deletePressed(ActionEvent event) {
        // The temps is here because without them it'll remove the first item, and won't be synchronized with the second
        String temp1 = listView1.getSelectionModel().getSelectedItem();
        String temp2 = listView2.getSelectionModel().getSelectedItem();
        listView1.getItems().remove(temp1);
        listView2.getItems().remove(temp2);
        pb.getTreeMap().remove(temp1);
    }

    @FXML
    void searchPressed(ActionEvent event) {
        int index = 0;
        String searched = nameTxt.getText();
        for (int i = 0; i < listView1.getItems().size(); i++) {
            if (searched.equals(listView1.getItems().get(i))) {
                index = i;
                listView1.scrollTo(index);
                listView1.getSelectionModel().select(index);
                break;
            } else {
                // TODO label not found
            }
        }

    }

    @FXML
    void updatePressed(ActionEvent event) {

    }
}
