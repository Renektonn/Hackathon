

if(e.getSource()==gamemode3){

      String event1 = "隔壁老王入侵民宅，強制性交未遂，今本庭詢問老王坦承不諱，且在通緝到案前有逃亡之事實。";
      char[] event1Chars = event1.toCharArray();

      Paint123Going PGthread = new Paint123Going(this,event1Chars ,Ta);
      PGthread.start();

      Ta.setBounds(800,100,500,500);
      Js.setBounds(800,100,500,500);
    }


class Paint123Going extends Thread {

    JFrame jf;
    char dCh[];
    JTextArea dTa;

    Paint123Going(JFrame F,char []Ch, JTextArea Cd) {
      dTa = Cd;
      dCh = Ch;
    }

    public void run() {
      
        for(int i=0; i<50; i++) {
          char myChar = dCh[i];
          String charToString = String.valueOf(myChar);
           dTa.append(charToString);

           try {
            sleep(100);
           }
           catch(InterruptedException e) {   
              System.out.println("sleep Exception !");
           }           


        }
    }

}
