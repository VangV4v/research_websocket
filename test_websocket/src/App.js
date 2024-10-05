import logo from './logo.svg';
import './App.css';
import { useState } from 'react';
import { StompSessionProvider, useStompClient, useSubscription } from 'react-stomp-hooks';
import { Stomp } from '@stomp/stompjs';

const ChildComponent = () => {
  const [message, setMessage] = useState("");
  const stompClient = useStompClient();
  // Subscribe to the topic that we have opened in our spring boot app
  useSubscription('/topic/message', (message) => {

    const convertToObject = JSON.parse(message.body);
    console.log(convertToObject);
    setMessage(convertToObject.message);
  });

  const client = Stomp.client("http://localhost:8080/ws-message");

  const handleSendMessage = () => {

    const data = {
      message: "Hello"
    }
    client.send('/app/sendMessage', {}, data);
    // stompClient.send('/app/sendMessage', {}, data);

    // stompClient.publish({
    //   destination: '/app/sendMessage',
    //   body: {
    //     data
    //   }
    // });
  };

  return (
    <>

      <div> The broadcast message from websocket broker is {message}</div>
      <button onClick={() => handleSendMessage()}>Click</button>
    </>

  )
}

function App() {
  return (
    <div className="App">
      <StompSessionProvider
        url={'http://localhost:8080/ws-message'}>
        <ChildComponent />
      </StompSessionProvider>
    </div>
  );
}

export default App;
