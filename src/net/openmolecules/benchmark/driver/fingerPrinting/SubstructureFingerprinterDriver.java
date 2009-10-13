package net.openmolecules.benchmark.driver.fingerPrinting;

import org.openscience.cdk.fingerprint.SubstructureFingerprinter;

import com.sun.japex.TestCase;


/**
 * @author jonalv
 *
 */
public class SubstructureFingerprinterDriver extends AbstractFingerprintDriver {

    @Override
    public void prepare(TestCase testCase) {
        
        super.prepare( testCase );
        fingerprinter = new SubstructureFingerprinter(); 
    }
}
