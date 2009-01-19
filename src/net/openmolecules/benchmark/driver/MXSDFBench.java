/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.openmolecules.benchmark.driver;

import com.metamolecular.mx.calc.MassCalculator;
import com.metamolecular.mx.io.mdl.SDFileReader;
import com.metamolecular.mx.model.Molecule;
import com.sun.japex.JapexDriverBase;
import com.sun.japex.TestCase;
import java.io.IOException;

/**
 *
 * @author rich
 */
public class MXSDFBench extends JapexDriverBase
{
  @Override
  public void prepare(TestCase testCase)
  {

  }

  @Override
  public void run(TestCase testCase)
  {
    double sum = 0;
    //double audit = 0;
    MassCalculator calculator = new MassCalculator();
    SDFileReader reader = createReader(testCase.getParam("japex.inputFile"));
    
    while (reader.hasNextRecord())
    {
      reader.nextRecord();

      Molecule molecule = reader.getMolecule();
      sum += calculator.findAveragedMass(molecule);
      //audit += Double.parseDouble(reader.getData("PUBCHEM_MOLECULAR_WEIGHT"));
    }
    
    reader.close();
  }
  
  private SDFileReader createReader(String filename)
  {
    SDFileReader result = null;
    
    try
    {
      result = new SDFileReader(filename);
    }
    
    catch(IOException e)
    {
      throw new RuntimeException(e);
    }
    
    return result;
  }
}
