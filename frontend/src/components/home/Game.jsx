import React, { useRef, useState,useEffect } from "react";
import { useFrame } from '@react-three/fiber';
import { Stars, Sparkles, KeyboardControls, Bounds, useProgress, Html} from '@react-three/drei';
import { useSpring, animated, config } from "@react-spring/three";
import { Physics, RigidBody } from '@react-three/rapier'
import Lights from "./Lights";
import Controller from 'ecctrl'
import { Ground } from "./Ground";
import { Wall } from "./Wall";
import { Wall2 } from "./Wall2";
import {Focus} from "./Focus";


 const Model = React.lazy(() => import('../models/Model'));
const Boy = React.lazy(() => import('../models/Boy'));
const Girl = React.lazy(() => import('../models/Girl'));
const Gentleman= React.lazy(() => import('../models/Gentleman'));
const Counter = React.lazy(() => import('../models/Counter'));
const Lamp = React.lazy(() => import('../models/Lamp'));
const ATM = React.lazy(() => import('../models/Atm'))

const Typewriter = React.lazy(() => import('../models/Typewriter'))
const Vault_door = React.lazy(() => import('../models/Vault_door'))
const Chandelier = React.lazy(() => import('../models/Chandelier'))
const Frame1 = React.lazy(() => import('../models/Frames1'))





function OscillatingMan(currentAction) {
    const myMesh = useRef();
    const { scale } = useSpring({
      scale: [0.15, 0.15, 0.15], 
      config: config.default
    });
  
    useFrame(({ clock }) => {
      const time_position = 5 * clock.getElapsedTime();
      const oscillation_position = Math.sin(time_position);
    });
  
    return (
      <animated.mesh
        scale={scale}
        ref={myMesh}
        position={[0, -0.95, 0]}
      >
        {/* <Model action={currentAction}/> */}
        <Boy action={currentAction}/>
        {/* <Girl action={currentAction}/> */}
        
      </animated.mesh>
    );
}

function Game() {

    const [currentAction, setCurrentAction] = useState("idle");
    const [pressedKeys, setPressedKeys] = useState(new Set());

    const keyboardMap = [
        { name: 'forward', keys: ['ArrowUp', 'KeyW'] },
        { name: 'backward', keys: ['ArrowDown', 'KeyS'] },
        { name: 'leftward', keys: ['ArrowLeft', 'KeyA'] },
        { name: 'rightward', keys: ['ArrowRight', 'KeyD'] },
        { name: 'run', keys: ['Space'] },
      ];

      const [cameraPos,setCameraPos]= useState({ x: -12, y: 12, z: -12 });
    const [targetPos, setTargetPos] = useState({ x: -12, y: 12, z: -12 });
    const [isMoving, setIsMoving] = useState(false);
    const moveCamera = (target) => {
      setTargetPos(target);
      setIsMoving(true);
    };
    useEffect(() => {
      if (isMoving) {
        const moveInterval = setInterval(() => {
          const newX = cameraPos.x + (targetPos.x - cameraPos.x) * 0.03;
          const newY = cameraPos.y + (targetPos.y - cameraPos.y) * 0.03;
          const newZ = cameraPos.z + (targetPos.z - cameraPos.z) * 0.03;
  
          setCameraPos({ x: newX, y: newY, z: newZ });

          if (
            Math.abs(newX - targetPos.x) < 0.01 &&
            Math.abs(newY - targetPos.y) < 0.01 &&
            Math.abs(newZ - targetPos.z) < 0.01
          ) {
            setIsMoving(false);
            clearInterval(moveInterval);
          }
        }, 5);
        return () => clearInterval(moveInterval);
      }}, [isMoving, cameraPos, targetPos]);
      
      const handleKeyDown = (event) => {
        const key = event.code;
        const find=keyboardMap.find((entry) => entry.keys.includes(key))
        if(find!=null){
          const keyMapping = find.name;
          setPressedKeys((prevKeys) => new Set([...prevKeys, keyMapping]));
          setCurrentAction(keyMapping);
        }
      };
      
      const handleKeyUp = (event) => {
        const key= event.code;
        const find=keyboardMap.find((entry) => entry.keys.includes(key))
        if(find!=null){
          const keyMapping = find.name;
          setPressedKeys((prevKeys) => new Set([...prevKeys].filter((k) => k !== keyMapping)));
          if(pressedKeys.size<2)
            setCurrentAction("idle");
        }
      };
    
      useEffect(()=>{
        
        document.addEventListener('keydown', handleKeyDown)
        document.addEventListener('keyup',handleKeyUp)
        return () => {
          document.removeEventListener('keydown', handleKeyDown);
          document.removeEventListener('keyup', handleKeyUp);
        };
      });
      
    return (
      <>
      <Lights />
      <Stars fade speed={7} radius={50} depth={30} count={1000} factor={4} />
      <Sparkles count={100} position={[0, 3, 0]} scale={18} size={20} speed={0.7} opacity={0.2} color="white"/>
      
      <Bounds observe margin={0.7}>
        <Focus>
        <Physics timeStep="vary">
            <KeyboardControls map={keyboardMap} >
              <Controller disableFollowCam={true} disableFollowCamPos={{ x: -12, y: 12, z: -12 }} maxVelLimit={5}>
                <RigidBody colliders={false} gravityScale={4}>
                    <OscillatingMan currentAction={currentAction} /> 
                </RigidBody>
              </Controller>
            </KeyboardControls>
                <RigidBody colliders={false} gravityScale={4}>
                  <Gentleman position={[-1,1.8,6.8]}scale= {[5, 5, 5]} rotation={[0,3.2,0]} />
                </RigidBody>
            <Frame1 rotation={[0, 4.7,0]} position={[7.5, 3, -8 ]} />
            <Counter position={[1.5,0,5]} scale={0.005} rotation={[0,3.2,0]} />
            <Counter position={[-3,0,5.25]} scale={0.005} rotation={[0,3.2,0]} />
            <Counter position={[-5,0,0]} scale={0.005} rotation={[0,1.57,0]} />
            {/* <Counter2 position={[-5,0,2]} scale={0.15} rotation={[0,1.57,0]} /> */}
            <Chandelier scale={0.01}  position={[0,8,0]}/>
            
            <Lamp position={[-5,2.7,1.5]} scale={2.5} rotation={[0,1.57,0]} />
            <Lamp position={[1,2.7,5]} scale={2.5} rotation={[0,1.57,0]} />
            <ATM scale={0.003} position={[7,0,1.5]} rotation={[0, -1.6, 0]}/>
        
        
            <Typewriter scale={5} position={[-5,3,-1]} rotation={[0, 3.2, 0]}/>
            <Vault_door scale={0.15} rotation={[0, 3.15, 0]} position={[2.5, 3.5, 7.2]} />
            <RigidBody type="fixed">
              <Ground />
              <Wall position={[8,4.5,0]} rotation={[1.57,0,1.57]}/>
              <Wall position={[0,4.5,8]} rotation={[1.57,0,3.14]}/>
              <Wall2 position={[0,3.5,-7]} rotation={[1.57,0,0]}/>
              <Wall2 position={[-7,3.5,0]} rotation={[1.57,0,1.57]}/>
            </RigidBody>
            
          </Physics>
        </Focus>
      </Bounds>

      <Ground />
      <Wall position={[8,4.5,0]} rotation={[1.57,0,1.57]}/>
      <Wall position={[0,4.5,8]} rotation={[1.57,0,3.14]}/>
      <Wall2 position={[0,3.5,-7]} rotation={[1.57,0,0]}/>
      <Wall2 position={[-7,3.5,0]} rotation={[1.57,0,1.57]}/>

      </>
    );
}

export default Game;