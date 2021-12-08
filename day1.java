class Scratch {

    public int findNumberOfIncreases(int[] input) {

        AtomicInteger count = new AtomicInteger(0);

         Arrays.stream(input)
                .reduce((left, right) ->  {
                        if (left < right) {
                            count.incrementAndGet();
                        }
                     return right;
                }).orElse(count.get());

         return count.get();
    }
    
    public int findNumberOfIncreasesSlidingWindow(int[] input, int window) {

        ArrayList<Integer> sumOfThree = new ArrayList<>();

        int inputLength = input.length;
        IntStream.rangeClosed(0, inputLength - window).boxed()
                .forEach(current -> sumOfThree.add((input[current] + input[current + 1] + input[current + 2])));

        int[] array = sumOfThree.stream().mapToInt(j -> j).toArray();
        return findNumberOfIncreases(array);
    }

    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File("depths.txt"));
        int [] depths = new int [2000];
        int i = 0;
        while(scanner.hasNextInt()) {
            depths[i++] = scanner.nextInt();
        }
        scanner.close();

        var scratch = new Scratch();
        System.out.println("Number: " + scratch.findNumberOfIncreases(depths));
    }
}
