import { BrowserRouter, Route, Switch, useRouteMatch } from 'react-router-dom';
import './App.css';
import Home from './component/Home';
import RoomDetail from './component/Room/RoomDetail';
function App() {
  return (
    <BrowserRouter>
      <Switch>
        <Route exact path="/" component={Home}></Route>
        <Route exact path={`/room/:id`} component = {RoomDetail}></Route>
      </Switch>

    </BrowserRouter>
  );
}

export default App;
