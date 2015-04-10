package com.next.newbo.support.smileypicker;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.next.newbo.utils.AppLogger;
import com.next.newbo.utils.Utils;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by NeXT on 15-4-10.
 */
public class Emoticons {

    public static final HashMap<String, String> EMOTICONS = new HashMap<>();
    public static final HashMap<String, Bitmap> EMOTICON_BITMAPS = new HashMap<>();
    public static final HashMap<String, Bitmap> EMOTICON_BITMAPS_SCALED = new HashMap<>();

    static {
        EMOTICONS.put("[挖鼻屎]", "kbsa_org.png");
        EMOTICONS.put("[泪]", "sada_org.png");
        EMOTICONS.put("[亲亲]", "qq_org.png");
        EMOTICONS.put("[晕]", "dizzya_org.png");
        EMOTICONS.put("[可爱]", "tza_org.png");
        EMOTICONS.put("[花心]", "hsa_org.png");
        EMOTICONS.put("[汗]", "han.png");
        EMOTICONS.put("[衰]", "cry.png");
        EMOTICONS.put("[偷笑]", "heia_org.png");
        EMOTICONS.put("[打哈欠]", "k_org.png");
        EMOTICONS.put("[睡觉]", "sleepa_org.png");
        EMOTICONS.put("[哼]", "hatea_org.png");
        EMOTICONS.put("[可怜]", "kl_org.png");
        EMOTICONS.put("[右哼哼]", "yhh_org.png");
        EMOTICONS.put("[酷]", "cool_org.png");
        EMOTICONS.put("[生病]", "sb_org.png");
        EMOTICONS.put("[馋嘴]", "cza_org.png");
        EMOTICONS.put("[害羞]", "shamea_org.png");
        EMOTICONS.put("[怒]", "angrya_org.png");
        EMOTICONS.put("[闭嘴]", "bz_org.png");
        EMOTICONS.put("[钱]", "money_org.png");
        EMOTICONS.put("[嘻嘻]", "tootha_org.png");
        EMOTICONS.put("[左哼哼]", "zhh_org.png");
        EMOTICONS.put("[委屈]", "wq_org.png");
        EMOTICONS.put("[鄙视]", "bs2_org.png");
        EMOTICONS.put("[吃惊]", "cj_org.png");
        EMOTICONS.put("[吐]", "t_org.png");
        EMOTICONS.put("[懒得理你]", "ldln_org.png");
        EMOTICONS.put("[思考]", "sk_org.png");
        EMOTICONS.put("[怒骂]", "nm_org.png");
        EMOTICONS.put("[哈哈]", "laugh.png");
        EMOTICONS.put("[抓狂]", "crazya_org.png");
        EMOTICONS.put("[抱抱]", "bba_org.png");
        EMOTICONS.put("[爱你]", "lovea_org.png");
        EMOTICONS.put("[鼓掌]", "gza_org.png");
        EMOTICONS.put("[悲伤]", "bs_org.png");
        EMOTICONS.put("[嘘]", "x_org.png");
        EMOTICONS.put("[呵呵]", "smilea_org.png");
        EMOTICONS.put("[感冒]", "gm.png");
        EMOTICONS.put("[黑线]", "hx.png");
        EMOTICONS.put("[愤怒]", "face335.png");
        EMOTICONS.put("[失望]", "face032.png");
        EMOTICONS.put("[做鬼脸]", "face290.png");
        EMOTICONS.put("[阴险]", "face105.png");
        EMOTICONS.put("[困]", "face059.png");
        EMOTICONS.put("[拜拜]", "face062.png");
        EMOTICONS.put("[疑问]", "face055.png");
        EMOTICONS.put("[赞]", "face329.png");
        EMOTICONS.put("[心]", "hearta_org.png");
        EMOTICONS.put("[伤心]", "unheart.png");
        EMOTICONS.put("[囧]", "j_org.png");
        EMOTICONS.put("[奥特曼]", "otm_org.png");
        EMOTICONS.put("[蜡烛]", "lazu_org.png");
        EMOTICONS.put("[蛋糕]", "cake.png");
        EMOTICONS.put("[弱]", "sad_org.png");
        EMOTICONS.put("[ok]", "ok_org.png");
        EMOTICONS.put("[威武]", "vw_org.png");
        EMOTICONS.put("[猪头]", "face281.png");
        EMOTICONS.put("[月亮]", "face18.png");
        EMOTICONS.put("[浮云]", "face229.png");
        EMOTICONS.put("[咖啡]", "face74.png");
        EMOTICONS.put("[爱心传递]", "face221.png");
        EMOTICONS.put("[来]", "face277.png");
        EMOTICONS.put("[熊猫]", "face002.png");
        EMOTICONS.put("[帅]", "face94.png");
        EMOTICONS.put("[不要]", "face274.png");
        EMOTICONS.put("[笑哈哈]", "lxh_xiaohaha.png");
        EMOTICONS.put("[好爱哦]", "lxh_haoaio.png");
        EMOTICONS.put("[噢耶]", "lxh_oye.png");
        EMOTICONS.put("[偷乐]", "lxh_toule.png");
        EMOTICONS.put("[泪流满面]", "lxh_leiliumanmian.png");
        EMOTICONS.put("[巨汗]", "lxh_juhan.png");
        EMOTICONS.put("[抠鼻屎]", "lxh_koubishi.png");
        EMOTICONS.put("[求关注]", "lxh_qiuguanzhu.png");
        EMOTICONS.put("[好喜欢]", "lxh_haoxihuan.png");
        EMOTICONS.put("[崩溃]", "lxh_bengkui.png");
        EMOTICONS.put("[好囧]", "lxh_haojiong.png");
        EMOTICONS.put("[震惊]", "lxh_zhenjing.png");
        EMOTICONS.put("[别烦我]", "lxh_biefanwo.png");
        EMOTICONS.put("[不好意思]", "lxh_buhaoyisi.png");
        EMOTICONS.put("[羞嗒嗒]", "lxh_xiudada.png");
        EMOTICONS.put("[得意地笑]", "lxh_deyidexiao.png");
        EMOTICONS.put("[纠结]", "lxh_jiujie.png");
        EMOTICONS.put("[给劲]", "lxh_feijin.png");
        EMOTICONS.put("[悲催]", "lxh_beicui.png");
        EMOTICONS.put("[甩甩手]", "lxh_shuaishuaishou.png");
        EMOTICONS.put("[好棒]", "lxh_haobang.png");
        EMOTICONS.put("[瞧瞧]", "lxh_qiaoqiao.png");
        EMOTICONS.put("[不想上班]", "lxh_buxiangshangban.png");
        EMOTICONS.put("[困死了]", "lxh_kunsile.png");
        EMOTICONS.put("[许愿]", "lxh_xuyuan.png");
        EMOTICONS.put("[丘比特]", "lxh_qiubite.png");
        EMOTICONS.put("[有鸭梨]", "lxh_youyali.png");
        EMOTICONS.put("[想一想]", "lxh_xiangyixiang.png");
        EMOTICONS.put("[躁狂症]", "lxh_kuangzaozheng.png");
        EMOTICONS.put("[转发]", "lxh_zhuanfa.png");
        EMOTICONS.put("[互相膜拜]", "lxh_xianghumobai.png");
        EMOTICONS.put("[雷锋]", "lxh_leifeng.png");
        EMOTICONS.put("[杰克逊]", "lxh_jiekexun.png");
        EMOTICONS.put("[玫瑰]", "lxh_meigui.png");
        EMOTICONS.put("[hold住]", "lxh_holdzhu.png");
        EMOTICONS.put("[群体围观]", "lxh_quntiweiguan.png");
        EMOTICONS.put("[推荐]", "lxh_tuijian.png");
        EMOTICONS.put("[赞啊]", "lxh_zana.png");
        EMOTICONS.put("[被电]", "lxh_beidian.png");
        EMOTICONS.put("[霹雳]", "lxh_pili.png");
        EMOTICONS.put("[doge]", "doge_org.gif");
        EMOTICONS.put("[喵喵]", "mm_org.gif");
        EMOTICONS.put("[笑cry]", "xiaoku_org.gif");
    }

    public static void init(Context context) {
        int size = Utils.getFontHeight(context, 16f);
        AppLogger.i("字的高度：" + size);
        AssetManager manager = context.getAssets();
        for (String key : EMOTICONS.keySet()) {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(manager.open(EMOTICONS.get(key)));
                EMOTICON_BITMAPS.put(key, bitmap);

                //根据文字高度缩放图片
                Matrix matrix = new Matrix();
                matrix.postScale((float) size / bitmap.getWidth(), (float) size / bitmap.getHeight());
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight());
                EMOTICON_BITMAPS_SCALED.put(key, bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //TODO init Adapter
    }

}
