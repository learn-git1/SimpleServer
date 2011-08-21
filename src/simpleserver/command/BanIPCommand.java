/*
 * Copyright (c) 2010 SimpleServer authors (see CONTRIBUTORS)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package simpleserver.command;

import static simpleserver.lang.Translations.t;
import simpleserver.Color;
import simpleserver.Player;
import simpleserver.Server;

public class BanIPCommand extends AbstractCommand implements PlayerCommand {
  public BanIPCommand() {
    super("banip IPADDRESS");
  }

  public void execute(Player player, String message) {
    String[] arguments = extractArguments(message);
    Server server = player.getServer();

    if (arguments.length >= 1) {
      Player p = player.getServer().findPlayer(arguments[0]);
      if (p == null) {
        server.ipBans.addBan(arguments[0]);
        player.addTMessage(Color.GRAY, "IP Address %s has been banned!", arguments[0]);
        server.adminLog("User " + player.getName() + " banned IP:\t "
            + arguments[0]);
      } else {
        server.ipBans.addBan(p.getIPAddress());
        server.kick(p.getName(), t("IP Banned!"));
        String msg = t("Player %s has been IP banned!", p.getName());
        server.runCommand("say", msg);
        server.adminLog("User " + player.getName() + " banned ip:\t "
            + arguments[0] + "\t(" + p.getName() + ")");
      }
    } else {
      player.addTMessage(Color.RED, "No player or IP specified.");
    }
  }

  @Override
  public void usage(Player player) {
    player.addTMessage(Color.GRAY, "Disallow all players with the IP address access to the server");
  }
}
