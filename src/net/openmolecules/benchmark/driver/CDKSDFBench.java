/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.openmolecules.benchmark.driver;

import com.sun.japex.JapexDriverBase;
import com.sun.japex.TestCase;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.io.iterator.IteratingMDLReader;
import org.openscience.cdk.qsar.descriptors.molecular.WeightDescriptor;
import org.openscience.cdk.qsar.result.DoubleResult;

/**
 *
 * @author rich
 */
public class CDKSDFBench extends JapexDriverBase
{

  @Override
  public void prepare()
  {
  }

  @Override
  public void run(TestCase testCase)
  {
    IteratingMDLReader reader = getReader(testCase.getParam("japex.inputFile"));
    WeightDescriptor calculator = new WeightDescriptor();
    double sum = 0;
    
    while (reader.hasNext())
    {
      IMolecule molecule = (IMolecule) reader.next();
      sum += (((DoubleResult)calculator.calculate(molecule).getValue()).doubleValue());
    }

    try
    {
      reader.close();
    }
    
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  private IteratingMDLReader getReader(String filename)
  {
    IteratingMDLReader result = null;

    try
    {
      Reader raw = new FileReader(filename);
      //BufferedReader reader = new BufferedReader(raw);

      result = new IteratingMDLReader(raw, DefaultChemObjectBuilder.getInstance());
    } catch (Exception e)
    {
      throw new RuntimeException(e);
    }

    return result;
  }
}
