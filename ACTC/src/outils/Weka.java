package outils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.BayesNet;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.Discretize;

public class Weka {


   Classifier arbre;
   Attribute Etat;
   Attribute MaxDepth;
   Attribute fov;
   Attribute DeltaMax;
   Attribute DeltaMoy;
   Instances dataset;

   public Weka(){
      try {
         initClassifier();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }


   public void initClassifier() throws NumberFormatException, IOException{
      FastVector valClasse=new FastVector(2);
      valClasse.addElement("Victoire");
      valClasse.addElement("Defaite");
      Etat=new Attribute("Etat",valClasse);
      MaxDepth=new Attribute("MaxDepth");
      fov = new Attribute("fov");
      DeltaMax = new Attribute("DeltaMax");
      DeltaMoy = new Attribute("DeltaMoy");
      FastVector rassemble=new FastVector(5);
      rassemble.addElement(Etat);
      rassemble.addElement(MaxDepth);
      rassemble.addElement(fov);
      rassemble.addElement(DeltaMoy);
      rassemble.addElement(DeltaMax);
      
      dataset=new Instances("exemple",rassemble,60);
      dataset.setClass(Etat);

      BufferedReader br = new BufferedReader(new FileReader("apprentissage3.csv"));
      String ligne = null;
      int z = 0;
      while ((ligne = br.readLine()) != null)
      {
         String[] data = ligne.split(",");
         if (z != 0) {
            Instance element=new Instance(5);

            element.setValue(Etat,data[0]);
            element.setValue(MaxDepth, Double.parseDouble(data[1]));
            element.setValue(fov,Double.parseDouble(data[2]));
            element.setValue(DeltaMoy,Double.parseDouble(data[3]));
            element.setValue(DeltaMax,Double.parseDouble(data[4]));
            element.setDataset(dataset);
            dataset.add(element);

         }
         z = z + 1;

      }
      br.close();
      arbre=new BayesNet();

      try {
		arbre.buildClassifier(dataset);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		//e.printStackTrace();
	}
   }

   public double isPI(String[] inst) throws Exception {


      Instance element=new Instance(5);

      element.setValue(MaxDepth,Double.parseDouble(inst[0]));
      element.setValue(fov,Double.parseDouble(inst[1]));
      element.setValue(DeltaMoy,Double.parseDouble(inst[2]));
      element.setValue(DeltaMax,Double.parseDouble(inst[3]));
      element.setDataset(dataset);
      
      double[] resultat = arbre.distributionForInstance(element);
      
      return  resultat[0];
   }
}