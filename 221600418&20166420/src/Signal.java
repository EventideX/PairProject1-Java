package src;

class Signal {
    private String inFile = "input.txt", outFile = "output.txt";
    private int wValue = 1, mValue = 1, nValue = 10;

    public Signal(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-h":
                case "--help":
                    help();
                    break;
                case "-i":
                    setInFile(args[++i]);
                    break;
                case "-o":
                    setOutFile(args[++i]);
                    break;
                case "-w":
                    setwValue(Integer.parseInt(args[++i]));
                    break;
                case "-m":
                    setmValue(Integer.parseInt(args[++i]));
                    break;
                case "-n":
                    setnValue(Integer.parseInt(args[++i]));
                    break;
                default:
                    help();
                    break;
            }
        }
    }

    public String getInFile() {
        return inFile;
    }

    private void setInFile(String inFile) {
        this.inFile = inFile;
    }

    public String getOutFile() {
        return outFile;
    }

    private void setOutFile(String outFile) {
        this.outFile = outFile;
    }

    public int getwValue() {
        return wValue;
    }

    private void setwValue(int wArg) {
        if (wArg != 0 && wArg != 1) {
            System.out.println("-w [0|1] \t\t与数字 0|1 搭配使用，用于表示是否采用不同权重：\r\n" +
                    "\t\t\t\t\t0 表示属于 Title、Abstract 的单词权重相同均为 1；\r\n" +
                    "\t\t\t\t\t1 表示属于 Title 的单词权重为10，属于 Abstract 单词权重为1。");
            System.out.println();
            wArg = 0;
        }
        this.wValue = 1 + wArg * 9;
    }

    public int getmValue() {
        return mValue;
    }

    private void setmValue(int mValue) {
        if (mValue <= 0) {
            System.out.println("-m [number] \t用于设置词组长度 [number] ≥ 1");
            System.out.println();
            mValue = 1;
        }
        this.mValue = mValue;
    }

    public int getnValue() {
        return nValue;
    }

    private void setnValue(int nValue) {
        if (nValue < 0 || nValue > 100) {
            System.out.println("-n [number] \t用于限制最终输出的单词(词组)的个数，表示输出频数最多的前 [number] 个单词(词组)，0 ≤ [number] ≤ 100");
            System.out.println();
            nValue = 1;
        }
        this.nValue = nValue;
    }

    private void help() {
        System.out.println("参数说明：\r\n" +
                "-h, --help \t\t显示帮助\r\n" +
                "-i [file] \t\t设定读入文件的存储路径\r\n" +
                "-o [file] \t\t设定生成文件的存储路径\r\n" +
                "-w [0|1] \t\t与数字 0|1 搭配使用，用于表示是否采用不同权重：\r\n" +
                "\t\t\t\t\t0 表示属于 Title、Abstract 的单词权重相同均为 1；\r\n" +
                "\t\t\t\t\t1 表示属于 Title 的单词权重为10，属于 Abstract 单词权重为1。\r\n" +
                "-m [number] \t用于设置词组长度\r\n" +
                "-n [number] \t用于限制最终输出的单词(词组)的个数，表示输出频数最多的前 [number] 个单词(词组)，0 ≤ [number] ≤ 100");
    }
}
