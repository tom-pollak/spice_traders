package com.mygdx.pirategame.logic;

import com.badlogic.gdx.maps.tiled.TiledMap;
import org.junit.Ignore;
import org.junit.Test;

public class BackgroundTiledMapTest {
    /**
     * Method under test: default or parameterless constructor of {@link BackgroundTiledMap}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testConstructor() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.badlogic.gdx.Files.internal(String)" because "com.badlogic.gdx.Gdx.files" is null
        //       at com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver.resolve(InternalFileHandleResolver.java:26)
        //       at com.badlogic.gdx.assets.loaders.AssetLoader.resolve(AssetLoader.java:42)
        //       at com.badlogic.gdx.maps.tiled.TmxMapLoader.load(TmxMapLoader.java:68)
        //       at com.badlogic.gdx.maps.tiled.TmxMapLoader.load(TmxMapLoader.java:59)
        //       at com.mygdx.pirategame.logic.BackgroundTiledMap.<init>(BackgroundTiledMap.java:17)
        //   In order to prevent <init>(TiledMap)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   <init>(TiledMap).
        //   See https://diff.blue/R013 to resolve this issue.

        new BackgroundTiledMap(new TiledMap());
    }

    /**
     * Method under test: default or parameterless constructor of {@link BackgroundTiledMap}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testConstructor2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.badlogic.gdx.Files.internal(String)" because "com.badlogic.gdx.Gdx.files" is null
        //       at com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver.resolve(InternalFileHandleResolver.java:26)
        //       at com.badlogic.gdx.assets.loaders.AssetLoader.resolve(AssetLoader.java:42)
        //       at com.badlogic.gdx.maps.tiled.TmxMapLoader.load(TmxMapLoader.java:68)
        //       at com.badlogic.gdx.maps.tiled.TmxMapLoader.load(TmxMapLoader.java:59)
        //       at com.mygdx.pirategame.logic.BackgroundTiledMap.<init>(BackgroundTiledMap.java:17)
        //   In order to prevent <init>(TiledMap)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   <init>(TiledMap).
        //   See https://diff.blue/R013 to resolve this issue.

        new BackgroundTiledMap(null);
    }
}

