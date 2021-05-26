package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AStarAlgorithmTest.class, DijkstraAlgorithmTest.class, OptimaliteTest.class})
public class AllTests {

}
