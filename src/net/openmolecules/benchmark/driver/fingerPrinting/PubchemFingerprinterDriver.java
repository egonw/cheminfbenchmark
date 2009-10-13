package net.openmolecules.benchmark.driver.fingerPrinting;

import org.openscience.cdk.fingerprint.MACCSFingerprinter;

import com.sun.japex.TestCase;


/**
 * @author jonalv
 *
 */
public class PubchemFingerprinterDriver extends AbstractFingerprintDriver {

    @Override
    public void prepare(TestCase testCase) {
        
        super.prepare( testCase );
        
        throw new RuntimeException("Not yet implemented");
//        fingerprinter = new PubchemFingerprinter(); 
    }
}
