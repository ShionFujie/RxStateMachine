# RxStateMachine
`stateMachine` is an operator on RxJava 2's `Observable` which transforms the source stream into the resulting stream that emits the `initialState` first and then sequentially applies a `reducer` to emits a new state from the previous state and an emission from the source stream.

## License
  Copyright 2019 Shion Tonatiuh Fujie

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
