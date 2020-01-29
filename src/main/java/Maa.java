import com.petersamokhin.bots.sdk.callbacks.Callback;
import com.petersamokhin.bots.sdk.clients.Group;

import org.jnity.vkbot.keyboard.Button;
import org.jnity.vkbot.keyboard.Keyboard;


public class Maa {

    static Group group;

    public static void main(String[] args) {
        group = new Group(177615124, "846d83508a2860d5968796923efc11210bb5453920caf3e874a208365d232284cc1658ad845cc3b6b0092");


        Keyboard keys = Keyboard.of(new Button("Открыть сайтик"));


        //keys.addButtons(new Button("MD5", ButtonColor.POSITIVE), new Button("SHA-256", ButtonColor.POSITIVE));
        //keys.addButtons(new Button("6", ButtonColor.PRIMARY),new Button("7", ButtonColor.PRIMARY),new Button("8", ButtonColor.PRIMARY),new Button("9",ButtonColor.PRIMARY));
       //keys.addButtons(new Button("1", ButtonColor.PRIMARY),new Button("10", ButtonColor.PRIMARY), new Button("11", ButtonColor.PRIMARY));
        keys.setInline(true);
        HASH hash = new HASH();
        System.out.println(keys.toJSON());
        group.onSimpleTextMessage((mess) -> {
            if (mess.getText().equals("!")) {
                mess.from(group).to(mess.authorId()).text("Доступные хешы:").keyboard(keys).send(new Callback[0]);
            }
});

 //       group.onSimpleTextMessage((mess) -> {
   //         try {
  //              String[] arg = mess.getText().split(" ");
   //             if (arg[0].equals("SHA-265")) {
    //            mess.from(group).to(mess.authorId()).text("Ваш хеш-пароль: " + hash.e_sha256(arg[1])).send(new Callback[0]);
    //            }
    //        }catch (ArrayIndexOutOfBoundsException e) {
    //            mess.from(group).to(mess.authorId()).text("Вы не ввели аргументы: " + e.getCause().toString()).send(new Callback[0]);
    //        }
    //    });
//
    }

}