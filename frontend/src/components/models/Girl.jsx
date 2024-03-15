/*
Auto-generated by: https://github.com/pmndrs/gltfjsx
Command: npx gltfjsx@6.2.16 girl.glb 
*/

import React, { useRef,useEffect } from 'react'
import { useGLTF, useAnimations } from '@react-three/drei'

export function Girl(props) {
  const group = useRef()
  const { nodes, materials, animations } = useGLTF('/resources/girl.glb')
  const { actions, mixer } = useAnimations(animations, group);

  useEffect(() => {
    
    const action = props.action.currentAction;
    mixer.stopAllAction();

    if (action === 'forward' || action ==='backward' || action ==='leftward'|| action==='rightward' || action==='run') {
      actions.walking.reset().play();
    } 
    else {
      actions.idle.reset().play();
    }
  }, [props.action.currentAction, actions, mixer]);
  return (
    <group ref={group} {...props} dispose={null}>
      <group name="Scene">
        <group name="Armature" rotation={[Math.PI / 2, 0, 0]} scale={0.01}>
          <primitive object={nodes.mixamorigHips} />
          <skinnedMesh name="GIRL_Cube001" geometry={nodes.GIRL_Cube001.geometry} material={materials['Girl.001']} skeleton={nodes.GIRL_Cube001.skeleton} />
          <skinnedMesh name="Plane" geometry={nodes.Plane.geometry} material={materials['Girl.001']} skeleton={nodes.Plane.skeleton} />
        </group>
      </group>
    </group>
  )
}

export default Girl;
useGLTF.preload('/resources/girl.glb')