import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

/**
 * The Tests class specifies a test battery implemented using the JUnit tool.
 * These tests use the test files from the Mooshak as input, generating as output
 * the expected result when running these tests.
 * This class is implemented for the Home Away from Home project.
 * However, its adaptation for the remaining problems to be performed
 * throughout the semester is trivial.
 * In order to use this class you must include the JUnit 4 library in your execution environment.
 * Ask for help in the lab sessions, if necessary!
 *
 * A classe Tests especifica um conjunto de testes implementado recorrendo 'a ferramenta 
 * JUnit. Estes testes usam como input os ficheiros de teste do Mooshak, gerando, como
 * output, o resultado esperado na execucao desses testes.
 * A classe esta implementada para os testes do projecto Home Away from Home.
 * No entanto, a sua adaptacao para os restantes problemas a realizar  
 * ao longo do semestre 'e trivial.
 * Para poder usar esta classe tem de incluir no seu ambiente de execucao a biblioteca JUnit 4.
 * Peca ajuda nas sessoes de laboratorio, se necessario!
 */
public class Tests {
    /**
     * Use the following lines to specify the tests you want to perform.
     * In this example file, created for the Home Away from Home project, we have 43 tests to perform.
     * For each input file, there is a corresponding output file. For example, the expected
     * result for the test inpu1 is output1. You do not need to do anything else in the
     * rest of the class. Just configure this sequence of tests! This is already done for
     * this project. For the other projects, you must configure the tests.
     *
     * Use as linhas que se seguem para especificar os testes que vai realizar.
     * Neste ficheiro de exemplo, criado para o projecto Home Away from Home, temos 43 testes a realizar.
     * Para cada ficheiro de input, existe um ficheiro de output correspondente. Por exemplo,
     * o resultado esperado para o input1 e output1. Nao tem de fazer mais nada no
     * resto da classe. Basta configurar esta sequencia de testes! Isto ja esta feito para este
     * projecto. Para os outros projectos, tem de configurar os testes.
     */
    @Test public void test01() { test("input01.txt","output01.txt"); }
    @Test public void test02() { test("input02.txt","output02.txt"); }
    @Test public void test03() { test("input03.txt","output03.txt"); }
    @Test public void test04() { test("input04.txt","output04.txt"); }
    @Test public void test05() { test("input05.txt","output05.txt"); }
    @Test public void test06() { test("input06.txt","output06.txt"); }
    @Test public void test07() { test("input07.txt","output07.txt"); }
    @Test public void test08() { test("input08.txt","output08.txt"); }
    @Test public void test09() { test("input09.txt","output09.txt"); }
    @Test public void test10() { test("input10.txt","output10.txt"); }
    @Test public void test11() { test("input11.txt","output11.txt"); }
    @Test public void test12() { test("input12.txt","output12.txt"); }
    @Test public void test13() { test("input13.txt","output13.txt"); }
    @Test public void test14() { test("input14.txt","output14.txt"); }
    @Test public void test15() { test("input15.txt","output15.txt"); }
    @Test public void test16() { test("input16.txt","output16.txt"); }
    @Test public void test17() { test("input17.txt","output17.txt"); }
    @Test public void test18() { test("input18.txt","output18.txt"); }
    @Test public void test19() { test("input19.txt","output19.txt"); }
    @Test public void test20() { test("input20.txt","output20.txt"); }
    @Test public void test21() { test("input21.txt","output21.txt"); }
    @Test public void test22() { test("input22.txt","output22.txt"); }
    @Test public void test23() { test("input23.txt","output23.txt"); }
    @Test public void test24() { test("input24.txt","output24.txt"); }
    @Test public void test25() { test("input25.txt","output25.txt"); }
    @Test public void test26() { test("input26.txt","output26.txt"); }
    @Test public void test27() { test("input27.txt","output27.txt"); }
    @Test public void test28() { test("input28.txt","output28.txt"); }
    @Test public void test29() { test("input29.txt","output29.txt"); }
    @Test public void test30() { test("input30.txt","output30.txt"); }
   /* @Test public void test31() { test("input31","output31"); }
    @Test public void test32() { test("input32","output32"); }
    @Test public void test33() { test("input33","output33"); }
    @Test public void test34() { test("input34","output34"); }
    @Test public void test35() { test("input35","output35"); }
    @Test public void test36() { test("input36","output36"); }
    @Test public void test37() { test("input37","output37"); }
    @Test public void test38() { test("input38","output38"); }
    @Test public void test39() { test("input39","output39"); }
    @Test public void test40() { test("input40","output40"); }
    @Test public void test41() { test("input41","output41"); }
    @Test public void test42() { test("input42","output42"); }
    @Test public void test43() { test("input43","output43"); }

    */


    /**
     * The BASE constant specifies the directory where the test files are located.
     */
    private static final File BASE = new File("tests");

    /**
     * The consoleStream variable is used to redirect the output of the program to the console.
     * The outContent variable is used to capture the output of the program.
     */
    private PrintStream consoleStream;
    /**
     * The outContent variable is used to capture the output of the program.
     */
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    /**
     * The setup method is executed before each test.
     * It redirects the output of the program to the console and captures the output of the program.
     */
    @Before
    public void setup() {
        consoleStream = System.out;
        System.setOut(new PrintStream(outContent));
    }

    /**
     * The test method is used to perform a test.
     * It receives as input the name of the input file and the name of the output file.
     * It reads the input file and the output file, and then executes the program with the input file.
     * It compares the output of the program with the expected output.
     * @param intput the name of the input file
     * @param output the name of the output file
     */
    public void test(String intput, String output) {
        test(new File(BASE, intput), new File(BASE, output));
    }

    /**
     * The test method is used to perform a test.
     * @param input the input file
     * @param output the output file
     */
    public void test(File input, File output) {
        consoleStream.println("Testing!");
        consoleStream.println("Input: " + input.getAbsolutePath());
        consoleStream.println("Output: " + output.getAbsolutePath());

        String fullInput = "", fullOutput = "";
        try {
            fullInput = new String(Files.readAllBytes(input.toPath()));
            fullOutput = new String(Files.readAllBytes(output.toPath()));
            consoleStream.println("INPUT ============");
            consoleStream.println(new String(fullInput));
            consoleStream.println("OUTPUT ESPERADO =============");
            consoleStream.println(new String(fullOutput));
            consoleStream.println("OUTPUT =============");
        } catch(Exception e) {
            e.printStackTrace();
            fail("Erro a ler o ficheiro");
        }

        try {
            Locale.setDefault(Locale.US);
            System.setIn(new FileInputStream(input));
            Class<?> mainClass = Class.forName("Main");
            mainClass.getMethod("main", String[].class).invoke(null, new Object[] { new String[0] });
        } catch (Exception e) {
            e.printStackTrace();
            fail("Erro no programa");
        } finally {
            byte[] outPrintBytes = outContent.toByteArray();
            consoleStream.println(new String(outPrintBytes));

            assertEquals(removeCarriages(fullOutput), removeCarriages(new String(outContent.toByteArray())));
        }
    }

    /**
     * The removeCarriages method is used to remove the carriage returns from a string.
     * @param s the string
     * @return the string without carriage returns
     */
    private static String removeCarriages(String s) {
        return s.replaceAll("\r\n", "\n");
    }

}