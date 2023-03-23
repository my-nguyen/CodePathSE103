package week2

import java.util.*


class Assessment9 {
    fun simplifyPath(path: String): String {
        return leetcode(path)
    }

    // Approach: Using Stacks
    // Intuition
    // This is a direct implementation of one of the most common commands used (in one form of the other) on all
    // the famous operating systems. For e.g. this directly translates to a part of the implementation of the cd command
    // functionality in the Unix OS. It basically helps us to change directory. Obviously, there's much more to it than
    // simply figuring out the smallest path of the final directory where the user wants to go. There is kernel level
    // implementation involved that actually uses the underlying file system to change the directory you are in to
    // the one where you want to go. You might argue that nobody would go crazy on the command line and want to run
    // something like:
    //    cd /a/b/c/.././././//d
    // However, our code needs to be able to handle the different scenarios and all the special characters like ., ..,
    // and /. All these weird scenarios will be taken care of by a couple of checks here and there. The main idea for
    // solving this problem would remain the same. The heading of this approach mentions the data structure that we are
    // going to use. However, we are going to work our way through the problem so as to understand as to why a stack
    // fits in here. Let's look at a tree-ish representation of a simple directory path.
    // Now suppose that to a path like /a/b/c, we add another component like /a/b/c/... Now, this is interesting because
    // the .. is no longer a sub-directory name. It has a special meaning and an indication to the operating system to
    // move up one level in the directory structure thus transforming the overall path to just /a/b. It's as if we popped
    // out the subdirectory c from the overall path. That's the core idea of this problem. If you think about it,
    // the only actionable special character is ... The single dot is kind of a no-op because it simply means the current
    // directory. So nothing changes in the overall path as such. Now that we have a general idea about how this problem
    // can be potentially solved using the stack, let's get right into the algorithm and discuss the solution.
    // Algorithm
    // 1. Initialize a stack, S that we will be using for our implementation.
    // 2. Split the input string using / as the delimiter. This step is really important because no matter what,
    //    the given input is a valid path and we simply have to shorten it. So, that means that whatever we have between
    //    two / characters is either a directory name or a special character and we have to process them accordingly.
    // 3. Once we are done splitting the input path, we will process one component at a time.
    // 4. If the current component is a . or an empty string, we will do nothing and simply continue. Well if you think
    //    about it, the split string array for the string /a//b would be [a,,b]. yes, that's an empty string in between
    //    a and b. Again, from the perspective of the overall path, it doesn't mean anything.
    // 5. If we encounter a double-dot .., we have to do some processing. This simply means go one level up in
    //    the current directory path. So, we will pop an entry from our stack if it's not empty.
    // 6. Finally, if the component we are processing right now is not one of the special characters, then we will simply
    //    add it to our stack because it's a legitimate directory name.
    // 7. Once we are done processing all the components, we simply have to connect all the directory names in our stack
    //    together using / as the delimiter and we will have our shortest path that leads us to the same directory as
    //    the one provided as an input.
    fun leetcode(path: String): String {
        val stack = Stack<String>()
        // Split the path using / as the delimiter
        val directories = path.split("/".toRegex()).toTypedArray()

        // process each directory one by one
        for (directory in directories) {
            if (directory == "." || directory.isEmpty()) {
                // no-op for a "." or an empty string
                continue
            } else if (directory == "..") {
                // If the current directory is a "..", then pop from the stack
                if (!stack.isEmpty()) {
                    stack.pop()
                }
            } else {
                // otherwise, it's a legitimate directory name, so add it to the stack
                stack.add(directory)
            }
        }

        // Stitch together all the directory names
        val result = StringBuilder()
        for (directory in stack) {
            result.append("/")
            result.append(directory)
        }
        return if (result.isNotEmpty()) result.toString() else "/"
    }

    private fun mine(path: String): String {
        if (path == "/../")
            return "/"

        val stack = Stack<String>()
        var i = 0
        var atEnd = false
        while (i < path.length && !atEnd) {
            when (path[i]) {
                '.' -> {
                    if (path[i+1] == '.' || path[i+1] == '/') {
                        stack.pop()
                        i += 3
                    } else if (path[i+1] == '/') {
                        i += 2
                    }
                }
                '/' -> {
                    if (i == path.lastIndex) {
                        atEnd = true
                    } else if (path[i + 1] == '/') {
                        i += 2
                    } else {
                        i += 1
                    }
                }
                else -> {
                    val folder = StringBuilder()
                    while (i < path.length && path[i] == '/') {
                        folder.append(path[i])
                        i++
                    }
                    stack.push(folder.toString())
                }
            }
        }

        val sb = StringBuilder()
        while (stack.isNotEmpty()) {
            sb.insert(0, stack.pop())
            sb.insert(0, "/")
        }
        return sb.toString()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val paths = arrayOf("/home/", "/../", "/home//foo/", "/a/./b/../../c/")
            for (path in paths) {
                val simplified = Assessment9().simplifyPath(path)
                println("path: $path, simplified: $simplified")
            }
        }
    }
}