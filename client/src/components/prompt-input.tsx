import {
  PromptInput,
  PromptInputSubmit,
  PromptInputTextarea,
  PromptInputToolbar,
  PromptInputTools,
} from "@/components/ui/shadcn-io/ai/prompt-input";
import { type FormEventHandler, useState } from "react";

interface AppPromptInputProps {
  submitCallback: (message: string) => void;
}

const AppPromptInput = (props: AppPromptInputProps) => {
  const [text, setText] = useState<string>("");
  const [status, setStatus] = useState<"submitted" | "streaming" | "ready" | "error">("ready");
  const handleSubmit: FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault();
    if (!text) {
      return;
    }
    props.submitCallback(text);
    setText("");
    setStatus("submitted");
    setTimeout(() => {
      setStatus("streaming");
    }, 200);
    setTimeout(() => {
      setStatus("ready");
      setText("");
    }, 2000);
  };
  return (
    <div
      className="p-8 w-full"
      style={{
        backgroundImage: "url('@/assets/modal-background.png')",
        backgroundSize: "cover",
        backgroundRepeat: "no-repeat",
        backgroundPosition: "center",
      }}
    >
      <PromptInput onSubmit={handleSubmit}>
        <PromptInputTextarea
          onChange={(e) => setText(e.target.value)}
          value={text}
          placeholder="Type your message..."
        />
        <PromptInputToolbar>
          <PromptInputTools></PromptInputTools>
          <PromptInputSubmit disabled={!text} status={status} />
        </PromptInputToolbar>
      </PromptInput>
    </div>
  );
};
export default AppPromptInput;
