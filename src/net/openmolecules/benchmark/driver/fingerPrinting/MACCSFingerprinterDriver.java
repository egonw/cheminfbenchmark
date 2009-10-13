package net.openmolecules.benchmark.driver.fingerPrinting;

import org.openscience.cdk.fingerprint.Fingerprinter;
import org.openscience.cdk.fingerprint.MACCSFingerprinter;

import com.sun.japex.TestCase;


/**
 * @author jonalv
 *
 */
public class MACCSFingerprinterDriver extends AbstractFingerprintDriver {

    @Override
    public void prepare(TestCase testCase) {
        
        super.prepare( testCase );
        
        fingerprinter = new MACCSFingerprinter(); 
    }
}
