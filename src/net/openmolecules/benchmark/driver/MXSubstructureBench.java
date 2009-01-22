/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.openmolecules.benchmark.driver;

import com.metamolecular.mx.io.Molecules;
import com.metamolecular.mx.io.mdl.SDFileReader;
import com.metamolecular.mx.map.DefaultMapper;
import com.metamolecular.mx.map.Mapper;
import com.metamolecular.mx.model.Molecule;
import com.sun.japex.JapexDriverBase;
import com.sun.japex.TestCase;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rich
 */
public class MXSubstructureBench extends JapexDriverBase
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
    Molecule query = Molecules.createBenzene();
    Mapper mapper = new DefaultMapper(query);
    int count = 0;
    
    for (Molecule target : molecules)
    {
      if (mapper.hasMap(target))
      {
        count++;
      }
    }
    
    System.out.println(count);
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
