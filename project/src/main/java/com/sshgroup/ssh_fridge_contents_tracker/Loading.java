package com.sshgroup.ssh_fridge_contents_tracker;

public class Loading implements Runnable {

    @Override
    public void run() {
        try {
            System.out.println();
            while (true) {
                System.out.print("\r                                    ");
                System.out.print("\rLoading: /");
                Thread.sleep(100);
                System.out.print("\r                                    ");
                System.out.print("\rLoading: -");
                Thread.sleep(100);
                System.out.print("\r                                    ");
                System.out.print("\rLoading: \\");
                Thread.sleep(100);
                System.out.print("\r                                    ");
                System.out.print("\rLoading: |");
                Thread.sleep(100);
            }
        }
        catch (InterruptedException e) {
            System.out.println("\r                                    ");
            System.out.println();
            System.out.println();
            Thread.currentThread().interrupt();
            return;
        }
    }
}
