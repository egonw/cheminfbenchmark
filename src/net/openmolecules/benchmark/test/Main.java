package net.openmolecules.benchmark.test;

import junit.framework.TestSuite;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import junit.textui.TestRunner;

/**
 * @author Richard L. Apodaca <rapodaca at metamolecular dot com>
 */
public class Main
{
  public static void main(String[] args)
  {
    TestSuite suite = new TestSuite();

    TestRunner.run(suite);
  }
}
