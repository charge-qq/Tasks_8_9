package org.example;

public class Console18{

    public static class InputArgs {
        public String inputFile;
        public String outputFile;
    }

    public static InputArgs parseCmdArgs(String[] args) {
        InputArgs params = new InputArgs();
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-i":
                case "--input-file":
                    params.inputFile = args[++i];
                    break;
                case "-o":
                case "--output-file":
                    params.outputFile = args[++i];
                    break;
            }
        }
        return params;
    }

    public static void main(String[] args) {
        InputArgs cmdArgs = parseCmdArgs(args);
        if (cmdArgs.inputFile == null || cmdArgs.outputFile == null) {
            System.err.println("Usage: java Task8 -i <input> -o <output>");
            System.exit(1);
        }

        try {
            int[][] field = Utils.Matrix_From_File(cmdArgs.inputFile);
            int result = Logic.checkWinner(field);
            Utils.writeResultToFile(result, cmdArgs.outputFile);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(2);
        }
    }
}