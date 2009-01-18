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
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.fingerprint.Fingerprinter;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.io.iterator.IteratingSMILESReader;
import org.openscience.cdk.smiles.SmilesParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Benchmark to compare fingerprint performance.
 * <p/>
 * This is primarily to compare performance between
 * CDK versions, though any toolkit that implements
 * hashed, path based fingerprints could be tested
 *
 * @author Rajarshi Guha
 */
public class FingerprintCDKBench extends JapexDriverBase {
    private List<IAtomContainer> mols;
    private SmilesParser smilesParser;
    private Fingerprinter fingerprinter;

    private int length = 6;
    private int depth = 1024;

    public void initializeDriver() {
    }

    public void prepare(TestCase testCase) {
        fingerprinter = new Fingerprinter(length, depth);
        smilesParser = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        String fileName = testCase.getParam("japex.inputFile");
        mols = new ArrayList<IAtomContainer>();
        try {
            IteratingSMILESReader reader = new IteratingSMILESReader(new FileInputStream(fileName));
            while (reader.hasNext()) mols.add((IAtomContainer) reader.next());
        } catch (FileNotFoundException e) {
        }
    }

    public void run(TestCase testCase) {
        for (IAtomContainer atomContainer : mols) {
            try {
                fingerprinter.getFingerprint(atomContainer);
            } catch (CDKException e) {
            }
        }
    }
}