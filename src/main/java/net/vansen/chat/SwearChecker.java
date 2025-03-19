package net.vansen.chat;

import org.jetbrains.annotations.NotNull;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SwearChecker {
    private static final Map<Character, String> replacements = new HashMap<>();

    static {
        replacements.put('a', "a𝒶𝓪𝕒𝖺𝐚𝗮𝙖𝚊ⓐ⒜ａΑаᴀ𝔞@4ɐäãåāăąȧảạầấậắặ");
        replacements.put('b', "b𝒷𝓫𝕓𝐛𝗯𝙗𝚋ⓑ⒝ｂ8ßḃɓƀ");
        replacements.put('c', "c𝒸𝓬𝕔𝐜𝗰𝙘𝚌ⓒ⒞ｃ¢©çćĉċčɔ");
        replacements.put('d', "d𝒹𝓭𝕕𝐝𝗱𝙙𝚍ⓓ⒟ｄḋđďɖɗ");
        replacements.put('e', "e𝒆𝓮𝕖𝖊𝐞𝗲𝙚𝚎ⓔ⒠ｅ3€℮èéêëēĕėęěȅȇẹẻếềệể");
        replacements.put('f', "f𝒻𝓯𝕗𝐟𝗳𝙛𝚏ⓕ⒡ｆƒḟ");
        replacements.put('g', "g𝓰𝓭𝕘𝐠𝗴𝙜𝚐ⓖ⒢ｇ6ɡģǧǵḡ");
        replacements.put('h', "h𝒽𝓱𝕙𝐡𝗵𝙝𝚑ⓗ⒣ｈḣḥɦ");
        replacements.put('i', "i𝒾𝓲𝕚𝐢𝗶𝙞𝚒ⓘ⒤ｉ¡1!|ılíìîïĩīĭįɨḭ");
        replacements.put('j', "j𝒿𝓳𝕛𝐣𝗷𝙟𝚓ⓙ⒥ｊĵǰ");
        replacements.put('k', "k𝓀𝓴𝕜𝐤𝗸𝙠𝚔ⓚ⒦ｋḱķǩḳ");
        replacements.put('l', "l𝓁𝓵𝕝𝐥𝗹𝙡𝚕ⓛ⒧ｌ1|7łľļḷḻ");
        replacements.put('m', "m𝓂𝓶𝕞𝐦𝗺𝙢𝚖ⓜ⒨ｍḿṁᵯ");
        replacements.put('n', "n𝓃𝓷𝕟𝐧𝗻𝙣𝚗ⓝ⒩ｎñńňņṅṇ");
        replacements.put('o', "o𝓸𝓸𝕠𝐨𝗼𝙤𝚘ⓞ⒪ｏΟоᴑօ0öòóôõøōŏőơọỏớờợở");
        replacements.put('p', "p𝓅𝓹𝕡𝐩𝗽𝙥𝚙ⓟ⒫ｐṕṗ");
        replacements.put('q', "q𝓆𝓺𝕢𝐪𝗾𝙦𝚚ⓠ⒬ｑɋʠ");
        replacements.put('r', "r𝓇𝓻𝕣𝐫𝗿𝙧𝚛ⓡ⒭ｒŕřŗṙṛ");
        replacements.put('s', "s𝓈𝓼𝕤𝐬𝗌𝙨𝚜ⓢ⒮ｓ5$šşśṡṣ");
        replacements.put('t', "t𝓉𝓽𝕥𝐭𝗍𝙩𝚝ⓣ⒯ｔ7+ťţṫṭ");
        replacements.put('u', "u𝓊𝓾𝕦𝐮𝗎𝙪𝚞ⓤ⒰ｕüùúûũūŭůűųưụủứ");
        replacements.put('v', "v𝓋𝓿𝕧𝐯𝗏𝙫𝚟ⓥ⒱ｖṽṿʋ");
        replacements.put('w', "w𝓌𝔀𝕨𝐰𝗐𝙬𝚠ⓦ⒲ｗẁẃŵẇẉ");
        replacements.put('x', "x𝓍𝔁𝕩𝐱𝗑𝙭𝚡ⓧ⒳ｘẋẍ");
        replacements.put('y', "y𝓎𝔂𝕪𝐲𝗒𝙮𝚣ⓨ⒴ｙýỳŷÿẏẙỵ");
        replacements.put('z', "z𝓏𝔃𝕫𝐳𝗓𝙯𝚣ⓩ⒵ｚźżžẓẕƶ");
        replacements.put('0', "0⓪０𝟢o");
        replacements.put('1', "1①１𝟣|i");
        replacements.put('2', "2②２𝟤");
        replacements.put('3', "3③３𝟥");
        replacements.put('4', "4④４𝟦");
        replacements.put('5', "5⑤５𝟧");
        replacements.put('6', "6⑥６𝟨");
        replacements.put('7', "7⑦７𝟩");
        replacements.put('8', "8⑧８𝟪");
        replacements.put('9', "9⑨９𝟫");
    }

    private static String build() {
        StringBuilder patternBuilder = new StringBuilder();

        for (String word : SwearList.list) {
            if (!patternBuilder.isEmpty()) {
                patternBuilder.append("|");
            }
            StringBuilder wordPattern = new StringBuilder();
            for (char c : word.toCharArray()) {
                wordPattern.append("[").append(replacements.get(c)).append("][\\W_]*");
            }
            patternBuilder.append("(?i)\\b").append(wordPattern).append("\\b");
        }

        return patternBuilder.toString();
    }

    public static String check(@NotNull String message) {
        String normalizedMessage = Normalizer.normalize(message, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        Matcher matcher = Pattern.compile(build(), Pattern.UNICODE_CHARACTER_CLASS).matcher(normalizedMessage);

        if (matcher.find()) {
            return matcher.group();
        }

        return null;
    }
}