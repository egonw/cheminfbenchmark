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

import com.metamolecular.mx.io.mdl.SDFileReader;
import com.metamolecular.mx.model.Molecule;
import com.metamolecular.mx.ring.HanserRingFinder;
import com.metamolecular.mx.ring.RingFinder;
import com.sun.japex.JapexDriverBase;
import com.sun.japex.TestCase;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Richard L. Apodaca
 */
public class MXRingBench extends JapexDriverBase
{

  private List<Molecule> molecules;

  @Override
  public void prepare(TestCase testCase)
  {
    molecules = new ArrayList();
    SDFileReader reader = createReader(testCase.getParam("japex.inputFile"));

    while (reader.hasNextRecord())
    {
      reader.nextRecord();
      
      Molecule molecule = reader.getMolecule(true);
      
      molecules.add(molecule);
    }

    reader.close();
  }

  @Override
  public void run(TestCase testCase)
  {
    RingFinder finder = new HanserRingFinder();
    
    for (Molecule molecule : molecules)
    {
      finder.findRings(molecule);
    }
  }

  private SDFileReader createReader(String filename)
  {
    SDFileReader result = null;

    try
    {
      result = new SDFileReader(filename);
    } catch (IOException e)
    {
      throw new RuntimeException(e);
    }

    return result;
  }
}
