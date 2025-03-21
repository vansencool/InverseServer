package net.vansen.chat;

import java.util.Base64;

/**
 * List by <a href="https://github.com/etylermoss/swears/blob/master/swears.txt">Etyler Moss</a>
 */
public class SwearList {
    public static String[] list;

    static {
        // noinspection all
        String swears = """
                MiBnaXJscyAxIGN1cAoyZzFjCjRyNWUKNWgxdAo1aGl0CmE1NQphX3NfcwphY3JvdG9tb3BoaWxpYQphbGFiYW1hIGhvdCBwb2NrZXQKYWxhc2thbiBwaXBlbGluZQphbGNvaG9saWMKYW1hdGV1cgphbmFsCmFuYWxwaGFiZXQKYW5hcmNoaXN0CmFuaWxpbmd1cwphbnVzCmFwZQphcGVzaGl0CmFyNWUKYXJyc2UKYXJzZQphcnNlaG9sZQphcnNlbGlja2VyCmFzcwphc3MgbWFzdGVyCmFzcy1mdWNrZXIKYXNzLWtpc3Nlcgphc3MtbnVnZ2V0CmFzcy13aXBlCmFzc2VzCmFzc2Z1Y2tlcgphc3NmdWtrYQphc3Nob2xlCmFzc2hvbGVzCmFzc211bmNoCmFzc3dob2xlCmF1dG8gZXJvdGljCmF1dG9lcm90aWMKYiF0Y2gKYjAwYnMKYjE3Y2gKYjF0Y2gKYmFiZWxhbmQKYmFieQpiYWJ5IGJhdHRlcgpiYWJ5IGp1aWNlCmJhY2t3b29kc21hbgpiYWxsIGdhZwpiYWxsIGdyYXZ5CmJhbGwga2lja2luZwpiYWxsIGxpY2tpbmcKYmFsbCBzYWNrCmJhbGwgc3Vja2luZwpiYWxsYmFnCmJhbGxzCmJhbGxzYWNrCmJhbmRpdApiYW5nYnJvcwpiYXJiYXIKYmFyZWJhY2sKYmFyZWx5IGxlZ2FsCmJhcmVuYWtlZApiYXN0YXJkCmJhc3RhcmRvCmJhc3RpbmFkbwpiYncKYmRzbQpiZWFuZXIKYmVhbmVycwpiZWFzdGlhbApiZWFzdGlhbGl0eQpiZWF2ZXIgY2xlYXZlcgpiZWF2ZXIgbGlwcwpiZWF2aXMKYmVnaW5uZXIKYmVsbGVuZApiZXN0aWFsCmJlc3RpYWxpdHkKYmkrY2gKYmlhdGNoCmJpZXN0CmJpZyBibGFjawpiaWcgYnJlYXN0cwpiaWcga25vY2tlcnMKYmlnIHRpdHMKYmltYm9zCmJpcmRsb2NrCmJpdGNoCmJpdGNoZXIKYml0Y2hlcnMKYml0Y2hlcwpiaXRjaGluCmJpdGNoaW5nCmJsYWNrIGNvY2sKYmxvbmRlIGFjdGlvbgpibG9uZGUgb24gYmxvbmRlIGFjdGlvbgpibG9vZHkKYmxvdyBqb2IKYmxvdyB5b3VyIGxvYWQKYmxvd2pvYgpibG93am9icwpibHViYmVyIGd1dApibHVlIHdhZmZsZQpibHVtcGtpbgpib2dleW1hbgpib2lvbGFzCmJvbGxvY2sKYm9sbG9ja3MKYm9sbG9rCmJvbmRhZ2UKYm9uZXIKYm9vYgpib29icwpib29ieQpib29vYnMKYm9vb29icwpib29vb29icwpib29vb29vb2JzCmJvb3R5IGNhbGwKYm9vemVyCmJvem8KYnJhaW4tZmFydApicmFpbmxlc3MKYnJhaW55CmJyZWFzdHMKYnJvbnRvc2F1cnVzCmJyb3duIHNob3dlcnMKYnJvd25pZQpicnVuZXR0ZSBhY3Rpb24KYnVjZXRhCmJ1Z2dlcgpidWdnZXIsIHNpbGx5CmJ1a2tha2UKYnVsbGR5a2UKYnVsbGV0IHZpYmUKYnVsbG9rcwpidWxsc2hpdApidW0KYnVtLWZ1Y2tlcgpidW5nIGhvbGUKYnVuZ2hvbGUKYnVubnkgZnVja2VyCmJ1c3R5CmJ1dHQKYnV0dGNoZWVrcwpidXR0ZnVja2VyCmJ1dHRoZWFkCmJ1dHRob2xlCmJ1dHRtdWNoCmJ1dHRwbHVnCmMwY2sKYzBja3N1Y2tlcgpjYWxsYm95CmNhbGxnaXJsCmNhbWVsCmNhbWVsIHRvZQpjYW1naXJsCmNhbXNsdXQKY2Ftd2hvcmUKY2FubmliYWwKY2FycGV0IG11bmNoZXIKY2FycGV0bXVuY2hlcgpjYXZlIG1hbgpjYXdrCmNoYWF2YW5pc3QKY2hhb3QKY2hhdXZpCmNoZWF0ZXIKY2hpY2tlbgpjaGlsZHJlbiBmdWNrZXIKY2hpbmsKY2hvY29sYXRlIHJvc2VidWRzCmNpcGEKY2lyY2xlamVyawpjbDF0CmNsZXZlbGFuZCBzdGVhbWVyCmNsaXQKY2xpdG9yaXMKY2xpdHMKY2xvdmVyIGNsYW1wcwpjbG93bgpjbHVzdGVyZnVjawpjbnV0CmNvY2sKY29jayBtYXN0ZXIKY29jayB1cApjb2NrLXN1Y2tlcgpjb2NrYm95CmNvY2tmYWNlCmNvY2tmdWNrZXIKY29ja2hlYWQKY29ja211bmNoCmNvY2ttdW5jaGVyCmNvY2tyb2FjaApjb2Nrcwpjb2Nrc3Vjawpjb2Nrc3Vja2VkCmNvY2tzdWNrZXIKY29ja3N1Y2tpbmcKY29ja3N1Y2tzCmNvY2tzdWthCmNvY2tzdWtrYQpjb2sKY29rbXVuY2hlcgpjb2tzdWNrYQpjb2t5CmNvbiBtZXJjaGFudApjb24tbWFuCmNvb24KY29vbnMKY29wcm9sYWduaWEKY29wcm9waGlsaWEKY29ybmhvbGUKY291bnRyeSBidW1wa2luCmNvdwpjb3gKY3JhcApjcmVhbXBpZQpjcmVlcApjcmV0aW4KY3JpbWluYWwKY3VtCmN1bW1lcgpjdW1taW5nCmN1bXMKY3Vtc2hvdApjdW5pbGluZ3VzCmN1bmlsbGluZ3VzCmN1bm5pbGluZ3VzCmN1bnQKY3VudCBzdWNrZXIKY3VudGxpY2sKY3VudGxpY2tlcgpjdW50bGlja2luZwpjdW50cwpjeWFsaXMKY3liZXJmdWMKY3liZXJmdWNrCmN5YmVyZnVja2VkCmN5YmVyZnVja2VyCmN5YmVyZnVja2VycwpjeWJlcmZ1Y2tpbmcKZDFjawpkYW1uCmRhcmtpZQpkYXRlIHJhcGUKZGF0ZXJhcGUKZGF5d2Fsa2VyCmRlYXRobG9yZApkZWVwIHRocm9hdApkZWVwdGhyb2F0CmRlbmRyb3BoaWxpYQpkZXJyIGJyYWluCmRlc3BlcmFkbwpkZXZpbApkaWNrCmRpY2toZWFkCmRpbGRvCmRpbGRvcwpkaW5nbGViZXJyaWVzCmRpbmdsZWJlcnJ5CmRpbmsKZGlua3MKZGlub3NhdXIKZGlyc2EKZGlydHkgcGlsbG93cwpkaXJ0eSBzYW5jaGV6CmRpc2d1ZXN0aW5nIHBhY2tldApkaXogYnJhaW4KZGxjawpkby1kbwpkb2cKZG9nIHN0eWxlCmRvZywgZGlydHkKZG9nLWZ1Y2tlcgpkb2dnaWUgc3R5bGUKZG9nZ2llc3R5bGUKZG9nZ2luCmRvZ2dpbmcKZG9nZ3kgc3R5bGUKZG9nZ3lzdHlsZQpkb2dzaGl0CmRvbGNldHQKZG9taW5hdGlvbgpkb21pbmF0cml4CmRvbW1lcwpkb25rZXkKZG9ua2V5IHB1bmNoCmRvbmtleXJpYmJlcgpkb29zaApkb3VibGUgZG9uZwpkb3VibGUgcGVuZXRyYXRpb24KZHAgYWN0aW9uCmRyYWt1bGEKZHJlYW1lcgpkcmlua2VyCmRydW5rYXJkCmRyeSBodW1wCmR1Y2hlCmR1ZnVzCmR1bGxlcwpkdW1ibwpkdW1teQpkdW1weQpkdmRhCmR5a2UKZWF0IG15IGFzcwplY2NoaQplZ29pc3QKZWphY3VsYXRlCmVqYWN1bGF0ZWQKZWphY3VsYXRlcwplamFjdWxhdGluZwplamFjdWxhdGluZ3MKZWphY3VsYXRpb24KZWpha3VsYXRlCmVyb3RpYwplcm90aXNtCmVzY29ydApldW51Y2gKZXhoaWJpdGlvbmlzdApmIHUgYyBrCmYgdSBjIGsgZSByCmY0bm55CmZfdV9jX2sKZmFnCmZhZ2dpbmcKZmFnZ2l0dApmYWdnb3QKZmFnZ3MKZmFnb3QKZmFnb3RzCmZhZ3MKZmFrZQpmYW5ueQpmYW5ueWZsYXBzCmZhbm55ZnVja2VyCmZhbnl5CmZhcm1lcgpmYXJ0CmZhcnQsIHNoaXR0eQpmYXRhc3MKZmF0c28KZmN1awpmY3VrZXIKZmN1a2luZwpmZWNhbApmZWNrCmZlY2tlcgpmZWxjaApmZWxjaGluZwpmZWxsYXRlCmZlbGxhdGlvCmZlbGxvdwpmZWx0Y2gKZmVtYWxlIHNxdWlydGluZwpmZW1kb20KZmliYmVyCmZpZ2dpbmcKZmluZ2VyYmFuZwpmaW5nZXJmdWNrCmZpbmdlcmZ1Y2tlZApmaW5nZXJmdWNrZXIKZmluZ2VyZnVja2VycwpmaW5nZXJmdWNraW5nCmZpbmdlcmZ1Y2tzCmZpbmdlcmluZwpmaXNoCmZpc3RmdWNrCmZpc3RmdWNrZWQKZmlzdGZ1Y2tlcgpmaXN0ZnVja2VycwpmaXN0ZnVja2luZwpmaXN0ZnVja2luZ3MKZmlzdGZ1Y2tzCmZpc3RpbmcKZml4ZXIKZmxha2UKZmxhbmdlCmZsYXNoIGhhcnJ5CmZvb2sKZm9va2VyCmZvb3QgZmV0aXNoCmZvb3Rqb2IKZnJlYWsKZnJvZwpmcm90dGluZwpmdWNrCmZ1Y2sgYnV0dG9ucwpmdWNrIGZhY2UKZnVjayBoZWFkCmZ1Y2sgbm9nZ2luCmZ1Y2thCmZ1Y2tlZApmdWNrZXIKZnVja2VycwpmdWNraGVhZApmdWNraGVhZHMKZnVja2luCmZ1Y2tpbmcKZnVja2luZ3MKZnVja2luZ3NoaXRtb3RoZXJmdWNrZXIKZnVja21lCmZ1Y2tzCmZ1Y2t0YXJkcwpmdWNrd2hpdApmdWNrd2l0CmZ1ZGdlIHBhY2tlcgpmdWRnZXBhY2tlcgpmdWsKZnVrZXIKZnVra2VyCmZ1a2tpbgpmdWtzCmZ1a3doaXQKZnVrd2l0CmZ1dGFuYXJpCmZ1eApmdXgwcgpnLXNwb3QKZ2FuZyBiYW5nCmdhbmdiYW5nCmdhbmdiYW5nZWQKZ2FuZ2JhbmdzCmdhbmdzdGVyCmdheSBzZXgKZ2F5bG9yZApnYXlzZXgKZ2VuaXRhbHMKZ2hvc3QKZ2lhbnQgY29jawpnaXJsIG9uCmdpcmwgb24gdG9wCmdpcmxzIGdvbmUgd2lsZApnb2F0Y3gKZ29hdHNlCmdvZApnb2QgZGFtbgpnb2QtZGFtCmdvZC1kYW1uZWQKZ29kZGFtbgpnb2RkYW1uZWQKZ29ra3VuCmdvbGRlbiBzaG93ZXIKZ29vIGdpcmwKZ29vZHBvb3AKZ29vc2UKZ29yZWdhc20KZ29yaWxsYQpncm9wZQpncm91Y2gKZ3JvdXAgc2V4CmdydW1weQpndXJvCmhhbmQgam9iCmhhbmRqb2IKaGFyZCBjb3JlCmhhcmRjb3JlCmhhcmRjb3Jlc2V4CmhlYWQsIGZhdApoZWxsCmhlbGwgZG9nCmhlbnRhaQpoZXNoZQpoaWxsYmlsbHkKaGlwcGllCmhvYXIKaG9hcmUKaG9lcgpob21vCmhvbW9lcm90aWMKaG9tb3NleHVhbApob25rZXkKaG9va2VyCmhvb2xpZ2FuCmhvcmUKaG9ybmllc3QKaG9ybnkKaG9yc2UgZnVja2VyCmhvdCBjYXJsCmhvdCBjaGljawpob3RzZXgKaG93IHRvIGtpbGwKaG93IHRvIG11cmRlcgpodWdlIGZhdApodW1waW5nCmlkaW90Cmlnbm9yYW11cwppbmNlc3QKaW50ZXJjb3Vyc2UKamFjayBvZmYKamFjay1hc3MKamFjay1vZmYKamFja29mZgpqYWlsIGJhaXQKamFpbGJhaXQKamFwCmplbGx5IGRvbnV0CmplcmsKamVyayBvZmYKamVyay1vZmYKamlnYWJvbwpqaWdnYWJvbwpqaWdnZXJib28KamlzbQpqaXoKaml6bQpqaXp6Cmpva2VyCmp1Z2dzCmp1bmtleQprYXdrCmtpa2UKa2lsbGVyCmtpbmJha3UKa2lua3N0ZXIKa2lua3kKa25vYgprbm9iYmluZwprbm9iZWFkCmtub2JlZAprbm9iZW5kCmtub2JoZWFkCmtub2Jqb2NreQprbm9iam9rZXkKa29jawprb25kdW0Ka29uZHVtcwprdW0Ka3VtbWVyCmt1bW1pbmcKa3VtcwprdW5pbGluZ3VzCmwzaStjaApsM2l0Y2gKbGFiaWEKbGFyZCBmYWNlCmxhdGNoa2V5IGNoaWxkCmxlYXJuZXIKbGVhdGhlciByZXN0cmFpbnQKbGVhdGhlciBzdHJhaWdodCBqYWNrZXQKbGVtb24gcGFydHkKbGlhcgpsb2xpdGEKbG9vc2VyCmxvdmVtYWtpbmcKbHVja3kKbHVtcHkKbHVzdApsdXN0aW5nCmx1emlmZXIKbTBmMAptMGZvCm00NXRlcmJhdGUKbWE1dGVyYjgKbWE1dGVyYmF0ZQptYWNobwptYWNrZXIKbWFrZSBtZSBjb21lCm1hbGUgc3F1aXJ0aW5nCm1hbiwgb2xkCm1hc29jaGlzdAptYXN0ZXItYmF0ZQptYXN0ZXJiOAptYXN0ZXJiYXQqCm1hc3RlcmJhdDMKbWFzdGVyYmF0ZQptYXN0ZXJiYXRpb24KbWFzdGVyYmF0aW9ucwptYXN0dXJiYXRlCm1lbmFnZSBhIHRyb2lzCm1pbGYKbWlueAptaXNzaW5nIGxpbmsKbWlzc2lvbmFyeSBwb3NpdGlvbgptby1mbwptb2YwCm1vZm8KbW9ua2V5Cm1vbnN0ZXIKbW90aGFmdWNrCm1vdGhhZnVja2EKbW90aGFmdWNrYXMKbW90aGFmdWNrYXoKbW90aGFmdWNrZWQKbW90aGFmdWNrZXIKbW90aGFmdWNrZXJzCm1vdGhhZnVja2luCm1vdGhhZnVja2luZwptb3RoYWZ1Y2tpbmdzCm1vdGhhZnVja3MKbW90aGVyIGZ1Y2tlcgptb3RoZXJmdWNrCm1vdGhlcmZ1Y2tlZAptb3RoZXJmdWNrZXIKbW90aGVyZnVja2Vycwptb3RoZXJmdWNraW4KbW90aGVyZnVja2luZwptb3RoZXJmdWNraW5ncwptb3RoZXJmdWNra2EKbW90aGVyZnVja3MKbW91bmQgb2YgdmVudXMKbXIgaGFuZHMKbXVja3kgcHViCm11ZmYKbXVmZiBkaXZlcgptdWZmZGl2aW5nCm11dGFudAptdXRoYQptdXRoYWZlY2tlcgptdXRoYWZ1Y2trZXIKbXV0aGVyCm11dGhlcmZ1Y2tlcgpuMWdnYQpuMWdnZXIKbmFtYmxhCm5hd2FzaGkKbmF6aQpuZWFuZGVydGhhbApuZWdybwpuZW9uYXppCm5lcmZoZWFyZGVyCm5pZyBub2cKbmlnZzNyCm5pZ2c0aApuaWdnYQpuaWdnYWgKbmlnZ2FzCm5pZ2dhegpuaWdnZXIKbmlnZ2VycwpuaW1waG9tYW5pYQpuaXBwbGUKbmlwcGxlcwpub2IKbm9iIGpva2V5Cm5vYmhlYWQKbm9iam9ja3kKbm9iam9rZXkKbm9ib2R5Cm5zZncgaW1hZ2VzCm51ZGUKbnVkaXR5Cm51bWJudXRzCm51cmQKbnV0cywgbnVtYgpudXRzYWNrCm55bXBobwpueW1waG9tYW5pYQpvY3RvcHVzc3kKb2RkYmFsbApvZ2VyCm9pbCBkaWNrCm9sZCBmYXJ0Cm9tb3Jhc2hpCm9uZSBjdXAgdHdvIGdpcmxzCm9uZSBndXkgb25lIGphcgpvcmFuZy11dGhhbgpvcmdhc2ltCm9yZ2FzaW1zCm9yZ2FzbQpvcmdhc21zCm9yZ3kKb3JpZ2luYWwKb3V0bGF3CnAwcm4KcGFjawpwYWVkb3BoaWxlCnBhaW4gaW4gdGhlIGFzcwpwYWtpCnBhbnRpZXMKcGFudHkKcGF2aWFuCnBhd24KcGVja2VyCnBlZG9iZWFyCnBlZG9waGlsZQpwZWdnaW5nCnBlbmNpbCBkaWNrCnBlbmlzCnBlbmlzZnVja2VyCnBlcnZlcnQKcGhvbmUgc2V4CnBob25lc2V4CnBodWNrCnBodWsKcGh1a2VkCnBodWtpbmcKcGh1a2tlZApwaHVra2luZwpwaHVrcwpwaHVxCnBpZWNlIG9mIHNoaXQKcGlnCnBpZ2Z1Y2tlcgpwaWdneS13aWdneQpwaW1waXMKcGlyYXRlCnBpc3MKcGlzcyBwaWcKcGlzc2VkCnBpc3NlcgpwaXNzZXJzCnBpc3NlcwpwaXNzZmxhcHMKcGlzc2luCnBpc3NpbmcKcGlzc29mZgpwaXNzcGlnCnBsYXlib3kKcGxlYXN1cmUgY2hlc3QKcG9sZSBzbW9rZXIKcG9ueXBsYXkKcG9vZgpwb29uCnBvb250YW5nCnBvb3AKcG9vcCBjaHV0ZQpwb29wY2h1dGUKcG9ybgpwb3Jubwpwb3Jub2ZyZWFrCnBvcm5vZ3JhcGh5CnBvcm5vcwpwcmljawpwcmlja3MKcHJpbmNlIGFsYmVydCBwaWVyY2luZwpwcm9sZXQKcHJvbgpwdGhjCnB1YmUKcHViZXMKcHVuYW55CnB1c3NlCnB1c3NpCnB1c3NpZXMKcHVzc3kKcHVzc3lzCnF1ZWFmCnF1ZWVmCnF1ZWVyCnF1ZXJ1bGFudApxdWltCnJhZ2hlYWQKcmFnaW5nIGJvbmVyCnJhcGUKcmFwaW5nCnJhcGlzdApyYXQKcmF0LWZpbmsKcmVjdHVtCnJlamVjdApyZXRhcmQKcmV2ZXJzZSBjb3dnaXJsCnJpZmYtcmFmZgpyaW1qYXcKcmltam9iCnJpbW1pbmcKcmlwcGVyCnJvYm90ZXIKcm9zeSBwYWxtCnJvc3kgcGFsbSBhbmQgaGVyIDUgc2lzdGVycwpyb3dkeQpydWZpYW4KcnVzdHkgdHJvbWJvbmUKcyBoaXQKcyZtCnMuby5iLgpzX2hfaV90CnNhY2sKc2FkaXNtCnNhZGlzdApzYW50b3J1bQpzYXByb3BoeXQKc2F0YW4Kc2NhcmFiCnNjYXQKc2NoZmluY3RlcgpzY2hsb25nCnNjaXNzb3JpbmcKc2NyZXdpbmcKc2Nyb2F0CnNjcm90ZQpzY3JvdHVtCnNlbWVuCnNleApzZXhvCnNleHkKc2ghKwpzaCF0CnNoMXQKc2hhZwpzaGFnZ2VyCnNoYWdnaW4Kc2hhZ2dpbmcKc2hhcmsKc2hhdmVkIGJlYXZlcgpzaGF2ZWQgcHVzc3kKc2hlbWFsZQpzaGkrCnNoaWJhcmkKc2hpdApzaGl0IGVhdGVyCnNoaXRibGltcApzaGl0ZGljawpzaGl0ZQpzaGl0ZWQKc2hpdGV5CnNoaXRmdWNrCnNoaXRmdWxsCnNoaXRoZWFkCnNoaXRpbmcKc2hpdGluZ3MKc2hpdHMKc2hpdHRlZApzaGl0dGVyCnNoaXR0ZXJzCnNoaXR0aW5nCnNoaXR0aW5ncwpzaGl0dHkKc2hvdGEKc2hyaW1waW5nCnNpbXVsYW50CnNrYW5rCnNrZWV0CnNrdW5rCnNrdXogYmFnCnNsYW50ZXllCnNsYXZlCnNsZWV6ZQpzbGVlemUgYmFnCnNsaW1lcgpzbGlteSBiYXN0YXJkCnNsdXQKc2x1dHMKc21hbGwgcHJpY2tlZApzbWVnbWEKc211dApzbmFpbApzbmFrZQpzbmF0Y2gKc25vYgpzbm90CnNub3diYWxsaW5nCnNvZG9taXplCnNvZG9teQpzb24gb2YgYSBiaXRjaCAKc29uLW9mLWEtYml0Y2gKc3BhYwpzcGljCnNwbG9vZ2UKc3Bsb29nZSBtb29zZQpzcG9vZ2UKc3ByZWFkIGxlZ3MKc3B1bmsKc3F1YXJlCnN0aW5rZXIKc3RyYXAgb24Kc3RyYXBvbgpzdHJhcHBhZG8Kc3RyaXAgY2x1YgpzdHJpcHBlcgpzdHVuawpzdHlsZSBkb2dneQpzdWNrCnN1Y2tzCnN1aWNpZGUgZ2lybHMKc3VsdHJ5IHdvbWVuCnN3YXN0aWthCnN3aW5kbGVyCnN3aW5lCnN3aW5nZXIKdDF0dDFlNQp0MXR0aWVzCnRhaW50ZWQgbG92ZQp0YXN0ZSBteQp0ZWEgYmFnZ2luZwp0ZWV0cwp0ZWV6CnRlbGV0dWJieQp0ZXN0aWNhbAp0ZXN0aWNsZQp0aGllZgp0aHJlZXNvbWUKdGhyb2F0aW5nCnRpZWQgdXAKdGlnaHQgd2hpdGUKdGl0CnRpdGZ1Y2sKdGl0cwp0aXR0CnRpdHRpZTUKdGl0dGllZnVja2VyCnRpdHRpZXMKdGl0dHkKdGl0dHlmdWNrCnRpdHR5d2Fuawp0aXR3YW5rCnRvaWxldHQgY2xlYW5lcgp0b25ndWUgaW4gYQp0b3BsZXNzCnRvc3Nlcgp0b3dlbGhlYWQKdHJhbm55CnRyaWJhZGlzbQp0dWIgZ2lybAp0dWJnaXJsCnR1cmQKdHVzaHkKdHVzc2kKdHc0dAp0d2F0CnR3YXRoZWFkCnR3YXR0eQp0d2luawp0d2lua2llCnR3byBnaXJscyBvbmUgY3VwCnR3dW50CnR3dW50ZXIKdHlwCnVuZHJlc3NpbmcKdW5saWtlCnVwc2tpcnQKdXJldGhyYSBwbGF5CnVyb3BoaWxpYQp2MTRncmEKdjFncmEKdmFnaW5hCnZhbXBpcgp2YW5kYWxlCnZhcm1pdAp2ZW51cyBtb3VuZAp2aWFncmEKdmlicmF0b3IKdmlvbGV0IHdhbmQKdm9yYXJlcGhpbGlhCnZveWV1cgp2dWx2YQp3MDBzZQp3YWxsZmxvd2VyCndhbmcKd2Fuawp3YW5rZXIKd2Fua2VyLCBibG9vZHkKd2Fua3kKd2VlemUgYmFnCndldCBkcmVhbQp3ZXRiYWNrCndoaXRlIHBvd2VyCndob2FyCndob3JlCndpZXJkbwp3aWxsaWVzCndpbGx5Cndpbm8Kd2l0Y2gKd29tYW5pemVyCndvb2R5IGFsbGVuCndvcm0Kd3JhcHBpbmcgbWVuCndyaW5rbGVkIHN0YXJmaXNoCnhlbmEKeGVub3BoZWJlCnhlbm9waG9iZQp4cmF0ZWQKeHh4Cnh4eCB3YXRjaGVyCnlhawp5YW9pCnllbGxvdyBzaG93ZXJzCnlldGkKeWlmZnkKeml0IGZhY2UKem9vcGhpbGlh
                """;
        list = new String(Base64.getDecoder().decode(swears.getBytes())).trim().split("\n");
    }
}