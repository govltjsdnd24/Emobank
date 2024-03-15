import { Route, Routes } from "react-router-dom";
import Nav from "./components/common/Nav";
import Home from "./pages/Home";
import MyPage from "./pages/MyPage";
import Forum from "./pages/Forum";
import Map from "./pages/Map";

import "./styles/common/App.css";

function App() {

  return (
    <div className="App" >
      <Nav />
      <Routes>
        <Route exact path="/" element={<Home />} />
        <Route path="/mypage" element={<MyPage />} />
        <Route path="/forum" element={<Forum />} />
        <Route path="/map" element={<Map />} />
      </Routes>
    </div>
  );
}

export default App;
