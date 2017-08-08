package emotion;

import emotion.train.Sample;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import org.opencv.core.Mat;
import org.opencv.core.Size;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author VakSF
 */
public class ClientController implements Initializable {
    
    @FXML
    private ImageView emotionImage;
    
    private TestOCV testOCV;
    private int size;
    
    private String lastDirectory = "nothing";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.size = 100;
        
        try {
            
            this.testOCV = new TestOCV(1728, this.size);
            
        } catch (URISyntaxException ex) {
            System.out.println("Error: " + ex.toString());
        }
    
    }
    
    @FXML
    private void training() throws URISyntaxException {
        this.testOCV.facesTraining();
    }
    
    private File file;
    
    @FXML
    private void chooseImage() {
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir archivo de imagen");
        fileChooser.getExtensionFilters().add(
                new ExtensionFilter("Archivos de imagen", "*.jpg", "*.jpeg", "*.png")
        );
        
        if (!lastDirectory.equals("nothing")) {
            fileChooser.setInitialDirectory(
                    new File(lastDirectory)
            );
        }
        
        File selectedFile = fileChooser.showOpenDialog(null);
        
        if (selectedFile != null) {
            
            lastDirectory = selectedFile.getParent();
            
            this.file = selectedFile;
            
            this.emotionImage.setImage(new Image(file.toURI().toString()));
            
        }
        
    }
    
    @FXML
    private void checkEmotion() {
        
        if (this.file != null) {
            
            String emotion = this.check(this.file);

            new Alert(
                    AlertType.INFORMATION,
                    "Emotion: " + emotion
            ).show();
            
        } else {
            
            new Alert(
                    AlertType.ERROR,
                    "Deber elegir una imagen"
            ).show();
            
        }
        
        
    }
    
    private String check(File file) {
        
        double[] confidences = new double[6];
        
        Mat scarface = this.testOCV.getScarface(file);
        Mat imageResize = this.testOCV.resize(scarface.clone(), new Size(size, size));
        Mat grayImage = this.testOCV.convertToGray(imageResize.clone());
        
        Sample sample = new Sample(testOCV.getHOGDescriptors(grayImage.clone()).toArray());
        
        int prediction = (int) testOCV.getSvmClassifier().predict(sample, confidences);
        
        return TestOCV.getKeysByValue(testOCV.getFaces(), prediction).iterator().next();
    }
    
    @FXML
    private void close(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
    
}
