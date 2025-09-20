import AppPromptInput from "@/components/prompt-input";
import {
  Conversation,
  ConversationContent,
  ConversationScrollButton,
} from "@/components/ui/shadcn-io/ai/conversation";
import { Message, MessageAvatar, MessageContent } from "@/components/ui/shadcn-io/ai/message";
import { nanoid } from "nanoid";
import { useEffect, useState } from "react";
import { v4 as uuidv4 } from "uuid";

const userAvatar = "https://api.dicebear.com/9.x/initials/svg?seed=MM&backgroundColor=43a047";
const aiAvatar = "https://api.dicebear.com/9.x/initials/svg?seed=AI&backgroundColor=43a047";

const AIChat: React.FC = () => {
  const [messages, setMessages] = useState<
    {
      key: string;
      value: string;
      name: string;
      avatar: string;
    }[]
  >([]);

  const [threadId, setThreadId] = useState<string>("");
  const sendMessage = async (message: string): Promise<ReadableStreamDefaultReader<Uint8Array>> => {
    const response = await fetch(`/agent/api/chat/message`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ content: message, thread_id: threadId }),
    });

    if (!response.ok || !response.body) {
      throw new Error("Failed to open response stream");
    }

    return response.body.getReader();
  };

  const newMessage = async (message: string) => {
    // Clear input
    const userMessage: {
      key: string;
      value: string;
      name: string;
      avatar: string;
    } = {
      key: nanoid(),
      value: message,
      name: "Max",
      avatar: userAvatar,
    };

    setMessages((prev) => [...prev, userMessage]);

    setMessages((prev) => [
      ...prev,
      {
        key: nanoid(),
        value: "",
        name: "AI",
        avatar: aiAvatar,
      },
    ]);

    try {
      const reader = await sendMessage(message);
      const decoder = new TextDecoder();
      let buffer = "";

      while (true) {
        const { done, value } = await reader.read();
        if (done) {
          break;
        }
        buffer += decoder.decode(value, { stream: true });
        const lines = buffer.split("\n");
        buffer = lines.pop()!;

        for (let line of lines) {
          if (!line.trim()) continue;

          try {
            // If using format like "data: {json...}", strip "data: "
            line = line.substring(5);
            const json = JSON.parse(line);
            const token = json.token;

            // Update assistant message atomically
            setMessages((prev) => {
              const newMessages = [...prev];
              const lastIndex = newMessages.length - 1;

              // Only update if it's an assistant message
              if (newMessages[lastIndex]?.name === "AI") {
                newMessages[lastIndex] = {
                  ...newMessages[lastIndex],
                  value: newMessages[lastIndex].value + token,
                };
              }

              return newMessages;
            });
          } catch (e) {
            console.warn("Invalid JSON chunk:", line, e);
          }
        }
      }
    } catch (err) {
      console.error("Streaming error:", err);
      setMessages((prev) =>
        prev.map((msg) =>
          messages.indexOf(msg) === messages.length - 1
            ? { ...msg, message: "[Error during streaming]" }
            : msg
        )
      );
    }
  };

  useEffect(() => {
    if (threadId) return;
    setThreadId(uuidv4());
  }, []);

  return (
    <div className="flex flex-col">
      <Conversation className="relative size-full" style={{ height: "498px" }}>
        <ConversationContent>
          {messages.map(({ key, value, name, avatar }, index) => (
            <Message from={index % 2 === 0 ? "user" : "assistant"} key={key}>
              <MessageContent>{value}</MessageContent>
              <MessageAvatar name={name} src={avatar} />
            </Message>
          ))}
        </ConversationContent>
        <ConversationScrollButton />
      </Conversation>
      <AppPromptInput submitCallback={newMessage} />
    </div>
  );
};

export default AIChat;
