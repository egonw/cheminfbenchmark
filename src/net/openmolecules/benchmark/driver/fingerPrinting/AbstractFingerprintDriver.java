package net.openmolecules.benchmark.driver.fingerPrinting;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.aromaticity.CDKHueckelAromaticityDetector;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.fingerprint.IFingerprinter;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.io.iterator.IteratingSMILESReader;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import com.sun.japex.JapexDriverBase;
import com.sun.japex.TestCase;


/**
 * @author jonalv
 *
 */
public abstract class AbstractFingerprintDriver extends JapexDriverBase {

    protected IFingerprinter fingerprinter;
    protected List<IAtomContainer> mols;
    
    @Override
    public void prepare( TestCase testCase ) {
    
        String fileName = testCase.getParam("japex.inputFile");
        String chemObjectBuilder = getParam("chemObjectBuilder");

        mols = new ArrayList<IAtomContainer>();
        try {
            Class<?> clazz = this.getClass().getClassLoader()
                                            .loadClass(chemObjectBuilder);
            Method getInstance = clazz.getMethod( "getInstance", 
                                                  new Class[]{} );
            IChemObjectBuilder builder 
                = (IChemObjectBuilder) getInstance.invoke( new Class[]{} );
            IteratingSMILESReader reader 
                = new IteratingSMILESReader( new FileInputStream(fileName), 
                                             builder );
            while (reader.hasNext()) {
                IAtomContainer tmp = (IAtomContainer) reader.next();
                AtomContainerManipulator
                    .percieveAtomTypesAndConfigureAtoms(tmp);
                CDKHueckelAromaticityDetector.detectAromaticity(tmp);
                mols.add(tmp);
            }
        } 
        catch (Exception e) {
            throw new RuntimeException("Exception occured", e);
        } 
    }
    
    @Override
    public void run( TestCase testCase ) {
        for (IAtomContainer atomContainer : mols) {
            try {
                fingerprinter.getFingerprint(atomContainer);                
            } 
            catch (CDKException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
