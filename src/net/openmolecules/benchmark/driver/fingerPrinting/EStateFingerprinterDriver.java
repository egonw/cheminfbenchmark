package net.openmolecules.benchmark.driver.fingerPrinting;

import org.openscience.cdk.fingerprint.EStateFingerprinter;

import com.sun.japex.TestCase;


/**
 * @author jonalv
 *
 */
public class EStateFingerprinterDriver extends AbstractFingerprintDriver {

    @Override
    public void prepare(TestCase testCase) {
        
        super.prepare( testCase );
        
        fingerprinter = new EStateFingerprinter(); 
    }
}
