package ascensionMod.patches;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.custom.CustomModeScreen;
import com.megacrit.cardcrawl.ui.panels.TopPanel;

import basemod.BaseMod;
import basemod.ReflectionHacks;


public class AscensionPatches 
{
	
	@SuppressWarnings("unchecked")
	private static Map<String, UIStrings> UiString = (Map<String, UIStrings>)BaseMod.gson.fromJson(loadJson("localization/eng/AscensionDesc.json"), getTrueType(UIStrings.class));
	
	private static UIStrings AscensionLevelStrings = UiString.get("AscensionModeDescriptions");
	
	private static String[] AscensionLevels = AscensionLevelStrings.TEXT;
	
	private static int AL;
	
	private static String loadJson(String jsonPath) {
        return Gdx.files.internal(jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));
    }
	
	private static Type getTrueType(Type type)
	{
		 @SuppressWarnings("rawtypes")
		HashMap TypeTokens = (HashMap)ReflectionHacks.getPrivateStatic(BaseMod.class, "typeTokens");
		 return (Type)TypeTokens.get(type);
	}
	
	@SpirePatch(
		cls = "com.megacrit.cardcrawl.screens.charSelect.CharacterOption",
		method = "updateHitbox"
	)
	public static class HitboxPatch {

		@SuppressWarnings("rawtypes")
		@SpireInsertPatch(
			rloc=62
		)
		public static SpireReturn Insert(CharacterOption __instance)
		{
			return SpireReturn.Return(null);
		}
		
		
		public static void Postfix(CharacterOption __instance) {
			
			if (20 < CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel) {
				CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel = 20;
			}
			if (0 > CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel) {
				CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel = 0;
			}
			
			int ascensionLevel = CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel;
		
				
			if ((ascensionLevel > 0) && !(ascensionLevel < 0)) {
				CardCrawlGame.mainMenuScreen.charSelectScreen.ascLevelInfoString = AscensionLevels[(ascensionLevel - 1)];
			}
			else {
				CardCrawlGame.mainMenuScreen.charSelectScreen.ascLevelInfoString = "";	
			}
		}
	}
	
	@SpirePatch(
		cls = "com.megacrit.cardcrawl.screens.charSelect.CharacterOption",
		method = "decrementAscensionLevel"
	)
	public static class decrementPatch {
		public static SpireReturn<?> Prefix(CharacterOption __instance, int level) {
			if(level < 0) {
				level = 0;
			}
			
			CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel = level;
			
			if(level > 15) {
				CardCrawlGame.mainMenuScreen.charSelectScreen.ascLevelInfoString = AscensionLevels[(level - 1)];
				return SpireReturn.Return(null);
			}
			else if(level == 0) {
				return SpireReturn.Return(null);
			}
			else {
				return SpireReturn.Continue();
			}
		}
	}
	
	@SpirePatch(
		cls = "com.megacrit.cardcrawl.screens.charSelect.CharacterOption",
		method = "incrementAscensionLevel"
	)
	public static class incrementPatch {
		/*@SpireInsertPatch(
			rloc=1
		)
		public static SpireReturn Insert(CharacterOption __instance)
		{
			return SpireReturn.Return(null);
		}*/
		
		public static SpireReturn<?> Prefix(CharacterOption __instance, int level) {			
			if(level > 20) {
				level = 20;
			}
			
			CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel = level;
			if(level > 15) {
				
				CardCrawlGame.mainMenuScreen.charSelectScreen.ascLevelInfoString = AscensionLevels[(level - 1)];
				return SpireReturn.Return(null);
			}
			else {
				return SpireReturn.Continue();
			}
		}
	}
	
	
	@SpirePatch(
		cls = "com.megacrit.cardcrawl.screens.custom.CustomModeScreen",
		method = "renderAscension"
	)
	public static class RenderPatch{

		@SpireInsertPatch(
			rloc=61
		)
		public static void Insert(CustomModeScreen __instance, SpriteBatch sb)
		{
			AL = __instance.ascensionLevel;
			__instance.ascensionLevel = 0;
		}
		
		public static void Postfix(CustomModeScreen __instance, SpriteBatch sb) {
			if(AL > 20) {
				AL = 20;
			}
			if(AL < 0) {
				AL = 0;
			}
			
			__instance.ascensionLevel = AL;

			
			if(__instance.ascensionLevel != 0) {
				Field screenXF = null;
				try {
					screenXF = CustomModeScreen.class.getDeclaredField("screenX");
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				screenXF.setAccessible(true);
				float screenX = 0;
				try {
					screenX = (float)screenXF.get("CustomModeScreen");
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Field ascensionModeHbF = null;
				try {
					ascensionModeHbF = CustomModeScreen.class.getDeclaredField("ascensionModeHb");
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ascensionModeHbF.setAccessible(true);
				Hitbox ascensionModeHb = null;
				try {
					ascensionModeHb = (Hitbox)ascensionModeHbF.get("CustomModeScreen");
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				FontHelper.renderSmartText(sb, FontHelper.charDescFont, CardCrawlGame.mainMenuScreen.charSelectScreen.ascLevelInfoString = AscensionLevels[(__instance.ascensionLevel - 1)], screenX + 475.0F * com.megacrit.cardcrawl.core.Settings.scale, ascensionModeHb.cY + 10.0F * com.megacrit.cardcrawl.core.Settings.scale, 9999.0F, 32.0F * com.megacrit.cardcrawl.core.Settings.scale, com.megacrit.cardcrawl.core.Settings.CREAM_COLOR);
			}
		}
	}
	
	@SpirePatch(
		cls = "com.megacrit.cardcrawl.ui.panels.TopPanel",
		method = "setupAscensionMode"
	)
	public static class SetupPatch{
		@SuppressWarnings("rawtypes")
		@SpireInsertPatch(
			rloc=5
		)
		public static SpireReturn Insert(TopPanel __instance)
		{
		    return SpireReturn.Return(null);
		}
		public static void Postfix(TopPanel __instance) {
			if (AbstractDungeon.isAscensionMode) {
				final StringBuilder sb = new StringBuilder();
				for (int i = 0; i < AbstractDungeon.ascensionLevel; ++i) {
					sb.append(AscensionLevels[i]);
					System.out.println(AscensionLevels[i]);
					if (i != AbstractDungeon.ascensionLevel - 1) {
						sb.append(" NL ");
					}
				}
				Field ascensionStringF = null;
				try {
					ascensionStringF = TopPanel.class.getDeclaredField("ascensionString");
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(sb.toString());
				ascensionStringF.setAccessible(true);
				//String x = sb.toString();
				try {
					ascensionStringF.set(__instance, sb.toString());
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
        }
	}
}
