/*
 * ChemBench - The Benchmark Suite for Cheminformatics
 * 
 * Copyright (c) 2009 Metamolecular, LLC (http://metamolecular.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
import org.openscience.cdk.ringsearch.AllRingsFinder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

/**
 * @author Richard L. Apodaca
 */
public class CDKRingBench extends JapexDriverBase
{
  private List<IAtomContainer> molecules;
  private AllRingsFinder finder;

  @Override
  public void prepare(TestCase testCase)
  {
    molecules = new ArrayList();
    finder = new AllRingsFinder();
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
    }
    
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void run(TestCase testCase)
  {
    int sum = 0;

    for (IAtomContainer molecule : molecules)
    {
      try
      {
        sum += finder.findAllRings(molecule).getAtomContainerCount();
      } catch (CDKException e)
      {
        throw new RuntimeException(e);
      }
    }
    
    System.out.println("sum=" + sum);
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
