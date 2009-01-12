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
import org.openscience.cdk.smiles.smarts.parser.SMARTSParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rajarshi Guha
 */
public class SMARTSParseCDKBench extends JapexDriverBase {
    private String[] patterns;

    public void initializeDriver() {
    }

    public void prepare(TestCase testCase) {
        List<String> tmp = new ArrayList<String>();
        try {
            String line;
            String fileName = testCase.getParam("japex.inputFile");
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            while ((line = in.readLine()) != null) tmp.add(line);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        patterns = tmp.toArray(new String[]{});
    }

    public void run(TestCase testCase) {
        int tally = 0;
        for (String pattern : patterns) {
            try {
                parse(pattern);
                tally++;
            } catch (Exception e) {

            }
        }
    }

    private void parse(String smarts) throws Exception {
        SMARTSParser parser = new SMARTSParser(new StringReader(smarts));
        parser.Start();
    }

}