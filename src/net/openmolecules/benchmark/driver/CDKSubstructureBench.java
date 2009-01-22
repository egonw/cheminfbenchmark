/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.openmolecules.benchmark.driver;

import com.sun.japex.JapexDriverBase;
import com.sun.japex.TestCase;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.io.iterator.IteratingMDLReader;
import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;
import org.openscience.cdk.templates.MoleculeFactory;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

/**
 *
 * @author rich
 */
public class CDKSubstructureBench extends JapexDriverBase
{

  private List<IAtomContainer> molecules;

  @Override
  public void prepare(TestCase testCase)
  {
    molecules = new ArrayList();
    IteratingMDLReader reader = getReader(testCase.getParam("japex.inputFile"));

    while (reader.hasNext())
    {
      IAtomContainer molecule = (IMolecule) reader.next();

      molecule = AtomContainerManipulator.removeHydrogens(molecule);
      molecules.add(molecule);
    }

    try
    {
      reader.close();
    } catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void run(TestCase testCase)
  {
    IMolecule query = MoleculeFactory.makeBenzene();
    int count = 0;

    try
    {
      for (IAtomContainer target : molecules)
      {
        if (UniversalIsomorphismTester.isSubgraph(target, query))
        {
          count++;
        }
      }
    } catch (CDKException e)
    {
      throw new RuntimeException(e);
    }

    System.out.println("count=" + count);
  }

  private IteratingMDLReader getReader(String filename)
  {
    IteratingMDLReader result = null;

    try
    {
      Reader raw = new FileReader(filename);

      result = new IteratingMDLReader(raw, DefaultChemObjectBuilder.getInstance());
    } catch (Exception e)
    {
      throw new RuntimeException(e);
    }

    return result;
  }
}
