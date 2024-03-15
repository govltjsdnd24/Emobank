import Game from "../components/home/Game"
import { Canvas } from "@react-three/fiber";
// import { useProgress, Html} from '@react-three/drei';
import { Loader, OrbitControls } from "@react-three/drei";
import { Suspense, lazy } from "react";
import * as THREE from 'three';
import { useThree, useLoader } from "@react-three/fiber";
import Clouds from "../components/home/Clouds";

// function Loading() {
//   const { progress } = useProgress()
//   return <Html center> 로딩중 {progress}%</Html>
// }

const BackGround = (props) => {
  const texture = useLoader(THREE.TextureLoader, "/assets/back.jpg");

  const { gl } = useThree();

  const formatted = new THREE.WebGLCubeRenderTarget(
    texture.image.height
  ).fromEquirectangularTexture(gl, texture);

  return <primitive attach="background" object={formatted.texture} />;
};

const Scene = lazy(() => {
  return new Promise(resolve => {
    setTimeout(() => resolve(import('../components/home/Game')), 5000)
  })
})

function Home() {
    return (
      <>
        <Canvas camera={{position:[-10,10,-10]}}>
        <color attach="background" args={["#1C1C1C"]} />
        {/* <OrbitControls 
          makeDefault
        /> */}
        <Suspense fallback={null}>
            <Game />
        </Suspense>
        </Canvas>
        <Loader />
      </>
    );
  }


export default Home;
