package net.openmolecules.benchmark.driver.fingerPrinting;

import org.openscience.cdk.fingerprint.ExtendedFingerprinter;
import org.openscience.cdk.fingerprint.Fingerprinter;

import com.sun.japex.TestCase;


/**
 * @author jonalv
 *
 */
public class ExtendedFingerprinterDriver extends AbstractFingerprintDriver {
    
    @Override
    public void prepare(TestCase testCase) {
        
        super.prepare( testCase );
        
        int length = Integer.parseInt( testCase.getParam("fplength") );
        int depth  = Integer.parseInt( testCase.getParam("fpdepth")  );

        fingerprinter = new ExtendedFingerprinter( length, depth ); 
    }
}
