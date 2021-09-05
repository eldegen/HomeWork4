package com.company;

import java.util.Random;

public class Main {

    public static int roundNumber = 1;
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence = "";
    public static String[] heroesAttackType = {
            "Physical", "Magical", "Kinetic", "Medic", "Golem"};
    public static double[] heroesHealth = {260, 270, 250, 300, 400};
    public static int[] heroesDamage = {15, 20, 25, 0, 5};

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            round();
        }
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0, 1, 2
        bossDefence = heroesAttackType[randomIndex];
        System.out.println("Boss choose " + bossDefence);
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0
                && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void round() {
        System.out.println("ROUND: " + roundNumber);
        chooseBossDefence();
        bossHits();
        golemReduce();
        heroesHit();
        medicsHeal();
        printStatistics();
        roundNumber++;
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(11); // 0,1,2,3,4,5,6,7,8
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    System.out.println("Critical Damage "
                            + heroesDamage[i] * coeff);
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println("============== STATS ==============");
        System.out.println("Boss Health: " + bossHealth + "; Boss Damage: " + bossDamage);
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(
                    heroesAttackType[i] + " Health: " + heroesHealth[i] + "; " + heroesAttackType[i] + " Damage: " + heroesDamage[i]);
        }
        System.out.println("===================================");
    }

    // Хилка медика
    public static void medicsHeal() {
        int healMin = 10;
        int healMax = 80;
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesHealth[i] < 100 && i != 3 && heroesHealth[i] >= 1 && heroesHealth[3] >= 1) {
                int randHeal = healMin + (int) (Math.random() * ((healMax - healMin) + 1));
                heroesHealth[i] = heroesHealth[i] + randHeal;

                System.out.println("============ SUPPORT ============");
                System.out.println("Medic heals someone: +" + randHeal + " HP");
                System.out.println("=================================");
                break;
            }
        }
    }

    // Задание на сообразительность

    // Герой Golem
    public static void golemReduce() {
        if (heroesHealth[4] > 0) {
            for (int i = 0; i < heroesHealth.length; i++) {
                heroesHealth[i] = heroesHealth[i] + (bossDamage * 0.2);
            }
            heroesHealth[4] = heroesHealth[4] - ((bossDamage * 0.2) * 2);
        }
    }
}
