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
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.smiles.smarts.parser.SMARTSParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Benchmark for graph isomorphism.
 *
 * The goal of this benchmark is to measure the performance
 * of subgraph isomorphism. Input is a collection of query and
 * target molecules provided in SMILES format. The test should
 * parse the SMILES into molecules and the actual run method will
 * simply perform the isomorphism test.
 *
 * Furthermore, this test just looks at single matches rather than
 * all matches.
 *
 * @author Rajarshi Guha
 */
public class IsomorphismCDKBench extends JapexDriverBase {
    private IAtomContainer[] targets;
    private IAtomContainer[] queries;

    public void initializeDriver() {
    }

    public void prepare(TestCase testCase) {
        List<String> tmp1 = new ArrayList<String>();
        List<String> tmp2 = new ArrayList<String>();
        try {
            String line;
            String fileName = testCase.getParam("japex.inputFile");
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            while ((line = in.readLine()) != null) {
                if (line.startsWith("#") || line.trim().length() == 0) continue;
                String[] toks = line.split(" ");
                tmp1.add(toks[0]);
                tmp2.add(toks[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        queries = new IAtomContainer[tmp1.size()];
        targets = new IAtomContainer[tmp1.size()];
        for (int i = 0; i < tmp1.size(); i++) {
            try {
                queries[i] = sp.parseSmiles(tmp1.get(i));
                targets[i] = sp.parseSmiles(tmp2.get(i));
            } catch (InvalidSmilesException e) {
            }
        }
    }

    public void run(TestCase testCase) {
        for (int i = 0; i < queries.length; i++) {
            try {
                UniversalIsomorphismTester.isSubgraph(targets[i], queries[i]);
            } catch (CDKException e) {
            }
        }
    }

}