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
import org.openscience.cdk.aromaticity.CDKHueckelAromaticityDetector;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.fingerprint.ExtendedFingerprinter;
import org.openscience.cdk.fingerprint.Fingerprinter;
import org.openscience.cdk.fingerprint.IFingerprinter;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.io.iterator.IteratingSMILESReader;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    private IFingerprinter fingerprinter;

    public void initializeDriver() {
    }

    public void prepare(TestCase testCase) {
        String fileName = testCase.getParam("japex.inputFile");
        int length = Integer.parseInt(testCase.getParam("fplength"));
        int depth = Integer.parseInt(testCase.getParam("fpdepth"));
        String chemObjectBuilder = getParam("chemObjectBuilder");
        String type = testCase.getParam("type");
        if (type.equals("standard")) fingerprinter = new Fingerprinter(length, depth);
        else if (type.equals("extended")) fingerprinter = new ExtendedFingerprinter(length, depth);

        mols = new ArrayList<IAtomContainer>();
        try {
        	Class clazz = this.getClass().getClassLoader()
        	    .loadClass(chemObjectBuilder);
        	Method getInstance = clazz.getMethod("getInstance", new Class[]{});
        	IChemObjectBuilder builder =
        		(IChemObjectBuilder)getInstance.invoke(new Class[]{});
            IteratingSMILESReader reader = new IteratingSMILESReader(
            	new FileInputStream(fileName), builder
            	
            );
            while (reader.hasNext()) {
                IAtomContainer tmp = (IAtomContainer) reader.next();
                AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(tmp);
                CDKHueckelAromaticityDetector.detectAromaticity(tmp);
                mols.add(tmp);
            }
        } catch (FileNotFoundException e) {
        } catch (CDKException e) {            
        } catch (ClassNotFoundException e) {
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
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