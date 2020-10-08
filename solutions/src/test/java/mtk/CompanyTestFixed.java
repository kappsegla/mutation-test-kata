package mtk;

import mtk.domain.CompanyFixed;
import mtk.domain.EmployeeFixed;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CompanyTestFixed
{
    private CompanyFixed company;

    @BeforeEach
    public void setUp()
    {
        this.company = new CompanyFixed("Megadyne, Inc.");
    }

    @AfterEach
    public void tearDown()
    {
        this.company = null;
    }

    @Test
    public void companyRenamed()
    {
        /*
         * TEST SMELL: Mocks are used unnecessarily and validate behavior rather than outcome
         *
         * Added assertion of the initial state - it is set outside of the test method and we want to confirm that our
         * assumption about the initial state is correct
         */
        assertEquals("Megadyne, Inc.", this.company.getName());

        String proposedName = "Cybertron Unlimited, Ltd.";
        this.company.setName(proposedName);

        assertEquals(proposedName, this.company.getName());
    }

    @Test
    public void leadingTrailingSpacesRemovedFromEmployeeName()
    {
        /*
         * TEST SMELL: Not testing all distinct scenarios (e.g., no spaces, spaces in the middle)
         */
        EmployeeFixed employee1 = new EmployeeFixed("001",    " Bob", 100_000.00);
        assertEquals("Bob", employee1.getName());

        EmployeeFixed employee2 = new EmployeeFixed("002", "Alice  ", 100_000.00);
        assertEquals("Alice", employee2.getName());

        EmployeeFixed employee3 = new EmployeeFixed("003", "Jimmy Don", 100_000.00);
        assertEquals("Jimmy Don", employee3.getName());
    }

    @Test
    public void employeeAdded()
    {
        /*
         * TEST SMELL: Irrelevant assertions
         */
        this.company.addEmployee(new EmployeeFixed("123", "Dave", 100_000.00));
        assertEquals(1, this.company.numberOfEmployees());

        this.company.addEmployee(new EmployeeFixed("456", "Bob", 50_000.00));
        assertEquals(2, this.company.numberOfEmployees());

        assertEquals("Dave", this.company.findEmployeeById("123").getName());
        assertEquals("Bob", this.company.findEmployeeById("456").getName());
    }

    @Test
    public void everybodyGetsRaise()
    {
        /*
         * TEST SMELL: Calculated expected value duplicates [incorrect] logic in th code under test
         */
        double increaseBy = 0.1; // everybody's salary should go up by this fraction

        this.company.addEmployee(new EmployeeFixed("123", "Dave",  100_000.00));
        this.company.addEmployee(new EmployeeFixed("456", "Alice", 120_000.00));
        this.company.addEmployee(new EmployeeFixed("789", "Bob",   110_000.00));

        this.company.everybodyGetsRaiseBy(increaseBy);

        EmployeeFixed dave = this.company.findEmployeeById("123");

        assertEquals(110_000.00, dave.getSalary(), 0.0001);
    }

    @Test
    public void findEmployeeById()
    {
        /*
         * TEST SMELL: No assertions
         */
        this.company.addEmployee(new EmployeeFixed("123", "Dave",  100_000.00));
        this.company.addEmployee(new EmployeeFixed("456", "Alice", 100_000.00));
        this.company.addEmployee(new EmployeeFixed("789", "Bob",   100_000.00));

        EmployeeFixed hopefullyDave = this.company.findEmployeeById("123");
        assertEquals("123", hopefullyDave.getId());
        assertEquals("Dave", hopefullyDave.getName());
        assertEquals(100_000.00, hopefullyDave.getSalary(), 0.00001);

        EmployeeFixed hopefullyNoOne = this.company.findEmployeeById("999");
        assertNull(hopefullyNoOne);
    }

    @Test
    public void employeeNameChanged()
    {
        /*
         * TEST SMELL: using a print/log statement, requires reading the output to determine the outcome of the test
         */
        this.company.addEmployee(new EmployeeFixed("123", "Dave",  100_000.00));
        this.company.addEmployee(new EmployeeFixed("456", "Alice", 100_000.00));
        this.company.addEmployee(new EmployeeFixed("789", "Bob",   100_000.00));

        EmployeeFixed employee = this.company.findEmployeeById("123");
        employee.setName("Tommy Lee");
        assertEquals("Tommy Lee", this.company.findEmployeeById("123").getName());
    }
}
