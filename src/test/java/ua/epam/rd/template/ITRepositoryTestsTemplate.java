/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.epam.rd.template;

import org.junit.Ignore;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

@Ignore
@ContextConfiguration(locations = {"classpath:/SpringConfig.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class ITRepositoryTestsTemplate extends RepositoryTestsTemplate {

}
